package com.equipsafelog.core.domain;

import java.util.Calendar;

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

@Entity
@Table(name = "terminal")
public class Terminal {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "terminal_seq")
	@SequenceGenerator(name = "terminal_seq", sequenceName = "terminal_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Column(name = "identity")
	private String identity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private Company company;

	@Column(name = "lastCommunication")
	private Calendar lastCommunication;

	@Column(name = "createDate")
	private Calendar createDate;
	
	@Column(name = "active")
	private Boolean active;

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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Calendar getLastCommunication() {
		return lastCommunication;
	}

	public void setLastCommunication(Calendar lastCommunication) {
		this.lastCommunication = lastCommunication;
	}

	public Calendar getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
}
