package com.equipsafelog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.equipsafelog.core.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}

