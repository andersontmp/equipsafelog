package com.equipsafelog.core.service;

import java.util.List;

import com.equipsafelog.core.domain.Company;

public interface CompanyService {

	Company saveCompany(Company company);

	List<Company> getAllCompanies(String user);

	Company getCompany(Long id);
	
	
	
}
