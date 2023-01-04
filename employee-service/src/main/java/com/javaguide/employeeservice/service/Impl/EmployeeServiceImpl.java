package com.javaguide.employeeservice.service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.javaguide.employeeservice.dto.DepartmentDto;
import com.javaguide.employeeservice.dto.EmployeeDto;
import com.javaguide.employeeservice.dto.EmployeeRespoDto;
import com.javaguide.employeeservice.entity.Employee;
import com.javaguide.employeeservice.feignClient.DepartmentClient;
import com.javaguide.employeeservice.repository.EmployeeRepository;
import com.javaguide.employeeservice.service.EmployeeService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service 
@AllArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
	private Environment env;
	EmployeeRepository dpyRepo;
	//RestTemplate restTemplate;
	//WebClient webclient;
	DepartmentClient departmentClient;
	@Override
	public void saveEmployee(EmployeeDto dptdtpo) {
		Employee dpt=new Employee();
		BeanUtils.copyProperties(dptdtpo, dpt);
		dpyRepo.save(dpt);
	}
	//@CircuitBreaker(name="${spring.application.name}", fallbackMethod="getDefaultDepartment")
	@Retry(name="${spring.application.name}", fallbackMethod="getDefaultDepartment")
	@Override
	public EmployeeRespoDto getDepartmentById(Long id) {
		log.info("inside getDepartmentById() method" );
		EmployeeRespoDto dptdtpo=new EmployeeRespoDto();
		Employee dpt =dpyRepo.getEmployeeById(id);
		if(dpt !=null) {
			BeanUtils.copyProperties(dpt, dptdtpo);
		}
		
		dptdtpo.setPort(env.getProperty("local.server.port"));   
		//ResponseEntity<DepartmentDto> dptData=restTemplate.getForEntity("http://localhost:8080/api/department/"+dpt.getDepartmentCode(), DepartmentDto.class);   
		//ResponseEntity<DepartmentDto> dptData=restTemplate.getForEntity("http://DEPARTMENT-SERVICE/api/department/"+dpt.getDepartmentCode(), DepartmentDto.class);   
		//dptdtpo.setDepartmentCode(dptData.getBody());
		
//		DepartmentDto dptData=webclient.get().uri("http://localhost:8080/api/department/"+dpt.getDepartmentCode()).retrieve()
//				.bodyToMono(DepartmentDto.class).block();
//		dptdtpo.setDepartmentCode(dptData);
		
	DepartmentDto dptData= departmentClient.getDepartmentByCode(dpt.getDepartmentCode());
	dptdtpo.setDepartmentCode(dptData); 
	
		return dptdtpo;
	}   
 public  EmployeeRespoDto getDefaultDepartment(Long id,Exception exception) {
		EmployeeRespoDto dptdtpo=new EmployeeRespoDto();
		Employee dpt =dpyRepo.getEmployeeById(id);
		if(dpt !=null) {
			BeanUtils.copyProperties(dpt, dptdtpo);
		}
	 
	 DepartmentDto dptData=new DepartmentDto();
	 dptData.setId(44L);
	 dptData.setDepartmentCode("2333");
	 dptData.setDepartmentName("R&D Dep");
	 dptData.setDepartmentDesc("default department");
	 dptdtpo.setDepartmentCode(dptData);
	 return dptdtpo;
 }
}
