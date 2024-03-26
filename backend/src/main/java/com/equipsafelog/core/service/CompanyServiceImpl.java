package com.equipsafelog.core.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.equipsafelog.core.domain.Company;
import com.equipsafelog.core.domain.User;
import com.equipsafelog.repository.CompanyRepository;
import com.equipsafelog.repository.UserRepository;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private UserRepository userRepository;

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
	public List<Company> getAllCompanies(String user) {
		if (user != null) {
			return companyRepository.findAllById(getCompaniesByUser(user));
		} else {
			return companyRepository.findAll();
		}
	}

	@Override
	public Company getCompany(Long id) {
		return companyRepository.findById(id).orElse(null);
	}

	private List<Long> getCompaniesByUser(String login) {
		if (login != null) {
			User userLogin = (User) userRepository.findByLogin(login);
			if (userLogin != null && userLogin.getCompanies() != null) {
				return userLogin.getCompanies();
			}
		}
		return Collections.EMPTY_LIST;
	}
}
