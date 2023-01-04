package com.javaguide.employeeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class EmployeeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceApplication.class, args);
	}
// @Bean
// @LoadBalanced  //add this if you are using eureka server to lad balance the request
//	public RestTemplate restTemplate() {
//		return new RestTemplate();
//	}
 
// @Bean
//	public WebClient webclient() {
//		return  WebClient.builder().build();
//	}
}
