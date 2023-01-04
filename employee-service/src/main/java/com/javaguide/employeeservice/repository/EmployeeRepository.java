package com.javaguide.employeeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaguide.employeeservice.entity.Employee;



public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	Employee getEmployeeById(Long departmentCode);
} 
