package com.javaguide.employeeservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeDto {
	private Long id;
	
	private String firstName;
	private String lastName;
	private String email;
	private String departmentCode;
}
