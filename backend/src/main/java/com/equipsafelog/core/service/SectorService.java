package com.equipsafelog.core.service;

import java.util.List;

import com.equipsafelog.core.domain.Sector;

public interface SectorService {

	Sector saveSector(Sector sector);

	List<Sector> getAllSectors();

	Sector getSector(Long id);

	List<Sector> getSectorByCompanyId(Long companyId);
	
	
	
}
