package com.equipsafelog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.equipsafelog.core.domain.Terminal;

public interface TerminalRepository extends JpaRepository<Terminal, Long> {
	
	@Query
	public List<Terminal> findByCompanyId(Long companyId);
}

