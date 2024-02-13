package com.equipsafelog.core.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipsafelog.core.domain.Terminal;
import com.equipsafelog.core.service.TerminalService;

@RestController
@RequestMapping("/terminal")
public class TerminalController {

	@Autowired
	private TerminalService terminalService;

	@GetMapping
	public List<Terminal> getAllTerminals() {
		return terminalService.getAllTerminals().parallelStream().map(f -> {
			f.setCompany(null);
			return f;
		}).collect(Collectors.toList());
	}

	@GetMapping(path = "/{id}")
	public Terminal getTerminal(@PathVariable Long id) {
		return terminalService.getTerminal(id);
	}

	@PostMapping
	public Terminal saveTerminal(@RequestBody Terminal inputEmployee) {
		return terminalService.saveTerminal(inputEmployee);
	}
	
	@GetMapping(path = "/company/{id}")
	public List<Terminal> getTerminalsByCompany(@PathVariable Long id) {
		return terminalService.getTerminalsByCompany(id).parallelStream().map(f -> {
			f.setCompany(null);
			return f;
		}).collect(Collectors.toList());
	}
}
