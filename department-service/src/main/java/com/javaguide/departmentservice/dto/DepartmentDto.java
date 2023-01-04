package com.javaguide.departmentservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DepartmentDto {
	private Long id;
	private String departmentName;
	private String departmentDesc;
	private String departmentCode;
	private String port;
}
