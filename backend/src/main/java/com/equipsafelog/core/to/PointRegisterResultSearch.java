package com.equipsafelog.core.to;

import java.util.Calendar;

public class PointRegisterResultSearch {

	private Long employeeId;

	private String employeeIdentity;
	
	private String employeeName;

	private Calendar date;

	private Long quantity;

	private Long sectorId;

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeIdentity() {
		return employeeIdentity;
	}

	public void setEmployeeIdentity(String employeeIdentity) {
		this.employeeIdentity = employeeIdentity;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	
	public String getEmployeeName() {
		return employeeName;
	}
	
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public void setSectorId(Long id) {
		this.sectorId = id;
	}
	
	public Long getSectorId() {
		return sectorId;
	}
}
