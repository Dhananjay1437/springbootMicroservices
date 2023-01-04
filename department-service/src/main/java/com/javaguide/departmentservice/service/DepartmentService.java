package com.javaguide.departmentservice.service;

import com.javaguide.departmentservice.dto.DepartmentDto;
import com.javaguide.departmentservice.entity.Department;

public interface DepartmentService {

	void saveDepartment(DepartmentDto dpt);
	DepartmentDto getDepartmentByCode( String departmentCode);
}
