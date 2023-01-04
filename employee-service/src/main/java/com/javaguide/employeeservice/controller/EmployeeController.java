package com.javaguide.employeeservice.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaguide.employeeservice.dto.EmployeeDto;
import com.javaguide.employeeservice.dto.EmployeeRespoDto;
import com.javaguide.employeeservice.service.EmployeeService;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController 
@RequestMapping("/api/employee")
public class EmployeeController {

	EmployeeService dptService;
	@PostMapping
	public void saveDepartment(@RequestBody EmployeeDto dptdtpo) {
		dptService.saveEmployee(dptdtpo);

	}
	@GetMapping("/{id}")
	public EmployeeRespoDto getDepartmentById(@PathVariable("id") Long id) {
		return  dptService.getDepartmentById(id);

	}
	@GetMapping("/hi")
	public String getDepartmentById() {
		return  "hi there";

	}
}
