package com.equipsafelog.core.domain;

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
@Table(name = "sector")
public class Sector {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sector_seq")
	@SequenceGenerator(name = "sector_seq", sequenceName = "sector_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "company")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private Company company;

	@Column(name = "minimalUse", nullable = false)
	private Integer minimalUse;

	@Column(name = "maximalUse", nullable = false)
	private Integer maximalUse;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Integer getMinimalUse() {
		return minimalUse;
	}

	public void setMinimalUse(Integer minimalUse) {
		this.minimalUse = minimalUse;
	}

	public Integer getMaximalUse() {
		return maximalUse;
	}

	public void setMaximalUse(Integer maximalUse) {
		this.maximalUse = maximalUse;
	}
	
	

}
