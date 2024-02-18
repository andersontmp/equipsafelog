package com.equipsafelog.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equipsafelog.core.domain.Company;
import com.equipsafelog.repository.CompanyRepository;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Override
	public Company saveCompany(Company company) {
		if (company != null) {
			if (company.getId() != null) {
				Company companyPersist = getCompany(company.getId());
				if (companyPersist != null) {
					companyPersist.setCnpj(company.getCnpj());
					companyPersist.setPhone(company.getPhone());
					companyPersist.setResponsable(company.getResponsable());
					companyPersist.setSocialName(company.getSocialName());
					companyPersist.setMinimalUse(company.getMinimalUse());
					companyPersist.setMaximalUse(company.getMaximalUse());
					companyPersist.setWeekendWork(company.getWeekendWork());
					return companyRepository.save(companyPersist);
				}
			} else {
				return companyRepository.save(company);
			}
		}
		return null;
	}

	@Override
	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	@Override
	public Company getCompany(Long id) {
		return companyRepository.findById(id).orElse(null);
	}
}
