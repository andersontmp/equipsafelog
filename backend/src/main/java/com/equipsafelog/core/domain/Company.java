package com.equipsafelog.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "company")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_seq")
	@SequenceGenerator(name = "company_seq", sequenceName = "company_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "socialName")
	private String socialName;
	
	@Column(name = "cnpj")
	private String cnpj;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "responsable")
	private String responsable;
	
	@Column(name= "weekendWork")
	private Boolean weekendWork;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSocialName() {
		return socialName;
	}

	public void setSocialName(String socialName) {
		this.socialName = socialName;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public Boolean getWeekendWork() {
		return weekendWork;
	}

	public void setWeekendWork(Boolean weekendWork) {
		this.weekendWork = weekendWork;
	}
	
}
