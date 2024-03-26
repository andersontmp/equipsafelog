package com.equipsafelog.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipsafelog.core.config.TokenService;
import com.equipsafelog.core.domain.Company;
import com.equipsafelog.core.service.CompanyService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private TokenService tokenService;
	
	private String getUser(HttpServletRequest request) {
		return tokenService.validateToken(request.getHeader("Authorization").replace("Bearer ", ""));
	}
	
	@GetMapping
	public List<Company> getAllCompanies(HttpServletRequest request) {
		return companyService.getAllCompanies(getUser(request));
	}

	@GetMapping(path = "/{id}")
	public Company getCompany(@PathVariable Long id) {
		return companyService.getCompany(id);
	}

	@PostMapping
	public Company saveCompany(@RequestBody Company inputCompany) {
		return companyService.saveCompany(inputCompany);
	}
}
