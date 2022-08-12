package com.NPHC.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
	@Id
	@Column(name = "id")
	private String empId;
	@Column(name = "login")
	private String empLogin;
	@Column(name = "name")
	private String empName;
	@Column(name = "salary")
	private double empSalary;
	@Column(name = "startDate")
	private Date startDate;
	
	
	public Employee(String empId, String empLogin, String empName, double empSalary, Date startDate) {
		super();
		this.empId = empId;
		this.empLogin = empLogin;
		this.empName = empName;
		this.empSalary = empSalary;
		this.startDate = startDate;
	}


}
