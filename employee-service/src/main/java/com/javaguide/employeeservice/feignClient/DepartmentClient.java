package com.javaguide.employeeservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.javaguide.employeeservice.dto.DepartmentDto;



//@FeignClient(url="http://localhost:8080",value="DEPARTMENT-SERVICE")
@FeignClient("DEPARTMENT-SERVICE")
public interface DepartmentClient {
	@GetMapping("/api/department/{departmentCode}")
	DepartmentDto getDepartmentByCode(@PathVariable String departmentCode);
}
