package com.equipsafelog.core.domain;

import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "point_register")
public class PointRegister {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "point_register_seq")
	@SequenceGenerator(name = "point_register_seq", sequenceName = "point_register_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name="employee")
	private Employee employee;
	
	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name="terminal")
	private Terminal terminal;
	
	@Column(name = "date")
	private Date date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}
}
