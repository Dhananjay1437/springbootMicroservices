package com.javaguide.departmentservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="department")
public class Department {
     @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String departmentName;
	private String departmentDesc;
	private String departmentCode;
}
