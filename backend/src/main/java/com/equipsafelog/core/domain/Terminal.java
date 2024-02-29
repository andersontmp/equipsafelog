package com.equipsafelog.core.domain;

import java.util.Date;

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
	@JoinColumn(name = "sector")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private Sector sector;

	@Column(name = "lastCommunication")
	private Date lastCommunication;

	@Column(name = "createDate")
	private Date createDate;

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
	
	public Sector getSector() {
		return sector;
	}
	
	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public Date getLastCommunication() {
		return lastCommunication;
	}

	public void setLastCommunication(Date lastCommunication) {
		this.lastCommunication = lastCommunication;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
