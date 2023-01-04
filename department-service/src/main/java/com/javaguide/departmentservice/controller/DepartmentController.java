package com.javaguide.departmentservice.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaguide.departmentservice.dto.DepartmentDto;

import com.javaguide.departmentservice.service.DepartmentService;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController 
@RequestMapping("/api/department")
public class DepartmentController {

	DepartmentService dptService;
	

	@PostMapping
	public void saveDepartment(@RequestBody DepartmentDto dptdtpo) {
		dptService.saveDepartment(dptdtpo);

	}
	@GetMapping("/{departmentCode}")
	public DepartmentDto getDepartmentByCode(@PathVariable("departmentCode") String departmentCode) {
		
		return  dptService.getDepartmentByCode(departmentCode);

	}

}
