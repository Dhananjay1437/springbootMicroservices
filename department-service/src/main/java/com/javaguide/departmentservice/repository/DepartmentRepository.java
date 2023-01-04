package com.javaguide.departmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaguide.departmentservice.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
	Department getDepartmentByDepartmentCode(String departmentCode);
} 
