package com.equipsafelog.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipsafelog.core.domain.Sector;
import com.equipsafelog.core.service.SectorService;

@RestController
@RequestMapping("/sector")
public class SectorController {

	@Autowired
	private SectorService sectorService;

	@GetMapping
	public List<Sector> getAllCompanies() {
		return sectorService.getAllSectors();
	}

	@GetMapping(path = "/{id}")
	public Sector getCompany(@PathVariable Long id) {
		return sectorService.getSector(id);
	}

	@PostMapping
	public Sector saveCompany(@RequestBody Sector inputCompany) {
		return sectorService.saveSector(inputCompany);
	}

	@GetMapping(path = "/company/{id}")
	public List<Sector> getSectorByCompanyId(@PathVariable Long id) {
		return sectorService.getSectorByCompanyId(id);
	}
}
