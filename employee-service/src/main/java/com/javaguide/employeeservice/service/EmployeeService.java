package com.javaguide.employeeservice.service;

import com.javaguide.employeeservice.dto.EmployeeDto;
import com.javaguide.employeeservice.dto.EmployeeRespoDto;

public interface EmployeeService {

	void saveEmployee(EmployeeDto dpt);
	EmployeeRespoDto getDepartmentById( Long id);
}
