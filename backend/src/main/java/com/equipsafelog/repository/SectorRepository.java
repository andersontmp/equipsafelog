package com.equipsafelog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.equipsafelog.core.domain.Sector;

public interface SectorRepository extends JpaRepository<Sector, Long> {
	
	@Query
	public List<Sector> findByCompanyId(Long companyId);

}
