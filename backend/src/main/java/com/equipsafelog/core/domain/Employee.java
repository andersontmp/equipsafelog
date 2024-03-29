package com.equipsafelog.core.domain;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
import jakarta.persistence.Transient;

@Entity
@Table(name = "employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
	@SequenceGenerator(name = "employee_seq", sequenceName = "employee_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;
	
	@Column
	private String name;

	@Column(name = "identity")
	private String identity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sector")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private Sector sector;

	@Column(name = "active")
	private Boolean active;

	@Transient
	private IDEmployee idEmployee;
	
	@Column(name = "id_employee")
	private Long idEmployeeId;

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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public IDEmployee getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(IDEmployee idEmployee) {
		this.idEmployee = idEmployee;
	}
	
	public Long getIdEmployeeId() {
		return idEmployeeId;
	}
	
	public void setIdEmployeeId(Long idEmployeeId) {
		this.idEmployeeId = idEmployeeId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, identity);
	}
	
	public Sector getSector() {
		return sector;
	}
	
	public void setSector(Sector sector) {
		this.sector = sector;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(id, other.id) && Objects.equals(identity, other.identity);
	}
}
