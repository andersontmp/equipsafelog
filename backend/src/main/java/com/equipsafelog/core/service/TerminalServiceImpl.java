package com.equipsafelog.core.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equipsafelog.core.domain.Terminal;
import com.equipsafelog.repository.TerminalRepository;

@Service
public class TerminalServiceImpl implements TerminalService {

	@Autowired
	private TerminalRepository terminalRepository;

	@Autowired
	private SectorService sectorService;

	@Override
	public List<Terminal> getAllTerminals() {
		return terminalRepository.findAll();
	}

	@Override
	public Terminal getTerminal(Long id) {
		return terminalRepository.findById(id).orElse(null);
	}

	@Override
	public Terminal saveTerminal(Terminal inputTerminal) {
		if (inputTerminal != null) {
			if (inputTerminal.getId() != null) {
				Terminal terminal = getTerminal(inputTerminal.getId());
				if (terminal != null) {
					terminal.setActive(inputTerminal.getActive());
					terminal.setIdentity(inputTerminal.getIdentity());
					if (inputTerminal.getSector() != null && inputTerminal.getSector().getId() != null) {
						terminal.setSector(sectorService.getSector(inputTerminal.getSector().getId()));
					}
					return terminalRepository.save(terminal);
				} else {
					return null;
				}
			} else {
				if (inputTerminal.getSector() != null && inputTerminal.getSector().getId() != null) {
					inputTerminal.setSector(sectorService.getSector(inputTerminal.getSector().getId()));
				}
				if (inputTerminal.getCreateDate() == null) {
					inputTerminal.setCreateDate(new Date());
				}
				return terminalRepository.save(inputTerminal);
			}
		}
		return null;
	}

	@Override
	public List<Terminal> getTerminalsByCompany(Long id) {
		return terminalRepository.findBySectorCompanyId(id);
	}
}
