package com.equipsafelog.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "id_employee")
public class IDEmployee {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_employee_seq")
	@SequenceGenerator(name = "id_employee_seq", sequenceName = "id_employee_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Column(name = "identity")
	private String identity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}
}
