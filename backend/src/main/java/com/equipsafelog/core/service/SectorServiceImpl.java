package com.equipsafelog.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equipsafelog.core.domain.Sector;
import com.equipsafelog.repository.SectorRepository;

@Service
public class SectorServiceImpl implements SectorService {

	@Autowired
	private SectorRepository sectorRepository;

	@Override
	public Sector saveSector(Sector sector) {
		return sectorRepository.save(sector);
	}

	@Override
	public List<Sector> getAllSectors() {
		return sectorRepository.findAll();
	}

	@Override
	public Sector getSector(Long id) {
		return sectorRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<Sector> getSectorByCompanyId(Long companyId){
		return sectorRepository.findByCompanyId(companyId);
	}

}
