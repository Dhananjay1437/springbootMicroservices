package com.javaguide.departmentservice.config;


import com.javaguide.departmentservice.aws.Container;
import com.javaguide.departmentservice.aws.Converter;
import com.javaguide.departmentservice.aws.EcsTaskMetadata;
import com.javaguide.departmentservice.aws.Network;
import com.netflix.appinfo.AmazonInfo;
import com.netflix.appinfo.AmazonInfo.MetaDataKey;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Configuration related to running service on AWS ECS Fargate. We overwrite IP
 * address which is registered to eureka server. We are using endpoint which is
 * defined in aws:
 * https://docs.aws.amazon.com/AmazonECS/latest/developerguide/task-metadata-endpoint.html
 * Note! Ensure that this is used in app profiles which are executed on AWS ECS
 * Fargate.
 */
@Configuration
public class CloudConfiguration {

	private final Logger log = LoggerFactory.getLogger(CloudConfiguration.class);
	private static final String AWS_API_VERSION = "v2";
	private static final String AWS_METADATA_URL = "http://169.254.170.2/" + AWS_API_VERSION + "/metadata";
	
	// Used as string.contains to search correct container
	// Make sure that your Docker container in AWS Task definition has this as part
	// of its name
	private static final String DOCKER_CONTAINER_NAME = "department-service";

	private final Environment env;

	@Qualifier("eurekaApplicationInfoManager")
	@Autowired
	ApplicationInfoManager applicationInfoManager;

	public CloudConfiguration(Environment env) {
		this.env = env;
	}

	/**
	 * We run service in fargate so override default IP when in fargate profile
	 * 
	 * @param inetUtils
	 * @return
	 */
	@Bean
	@Profile("dev")
	public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) throws Exception {
		log.info("Customize EurekaInstanceConfigBean for AWS");
		log.info("Docker container should have name containing " + DOCKER_CONTAINER_NAME);

		EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);

		try {
			String json = readEcsMetadata();
			EcsTaskMetadata metadata = Converter.fromJsonString(json);
			String ipAddress = findContainerPrivateIP(metadata);
			log.info("Override ip address to " + ipAddress);
			config.setIpAddress(ipAddress);
			config.setNonSecurePort(getPortNumber());
			config.setHostname(ipAddress);
			config.setInstanceId(ipAddress + ":" + DOCKER_CONTAINER_NAME + ":" + getPortNumber());
		} catch (Exception ex) {
			log.info("Something went wrong when reading ECS metadata: " + ex.getMessage());
		}
		return config;
	}

	private int getPortNumber() {
		return env.getProperty("server.port", Integer.class);
	}

	private String findContainerPrivateIP(EcsTaskMetadata metadata) {
		if (null != metadata) {
			for (Container container : metadata.getContainers()) {
				boolean found = container.getName().toLowerCase().contains(DOCKER_CONTAINER_NAME);
				if (found) {
					Network network = container.getNetworks()[0];
					return network.getIPv4Addresses()[0];
				}
			}
		}
		return "";
	}

	private String readEcsMetadata() throws IOException {
		String url = AWS_METADATA_URL;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		StringBuilder response = new StringBuilder();
		try {
			con.setRequestMethod("GET");
			try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
			}
		} finally {
			con.disconnect();
		}
		return response.toString();
	}

}
