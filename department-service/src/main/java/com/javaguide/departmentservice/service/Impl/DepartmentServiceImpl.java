package com.javaguide.departmentservice.service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.javaguide.departmentservice.dto.DepartmentDto;
import com.javaguide.departmentservice.entity.Department;
import com.javaguide.departmentservice.repository.DepartmentRepository;
import com.javaguide.departmentservice.service.DepartmentService;

import lombok.AllArgsConstructor;

@Service 
@AllArgsConstructor

public class DepartmentServiceImpl implements DepartmentService {
	private Environment env;
	DepartmentRepository dpyRepo;
	@Override
	public void saveDepartment(DepartmentDto dptdtpo) {
		Department dpt=new Department();
		BeanUtils.copyProperties(dptdtpo, dpt);
		dpyRepo.save(dpt);
	}
	@Override
	public DepartmentDto getDepartmentByCode(String departmentCode) {
		DepartmentDto dptdtpo=new DepartmentDto();
		Department dpt =dpyRepo.getDepartmentByDepartmentCode(departmentCode);
		BeanUtils.copyProperties(dpt, dptdtpo);
		dptdtpo.setPort(env.getProperty("local.server.port")); 
		return dptdtpo;
	} 

}
