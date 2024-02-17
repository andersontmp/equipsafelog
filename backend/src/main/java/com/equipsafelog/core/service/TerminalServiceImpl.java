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
	private CompanyService companyService;

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
					if (inputTerminal.getCompany() != null && inputTerminal.getCompany().getId() != null) {
						terminal.setCompany(companyService.getCompany(inputTerminal.getCompany().getId()));
					}
					return terminalRepository.save(terminal);
				} else {
					return null;
				}
			} else {
				if (inputTerminal.getCompany() != null && inputTerminal.getCompany().getId() != null) {
					inputTerminal.setCompany(companyService.getCompany(inputTerminal.getCompany().getId()));
				}
				if(inputTerminal.getCreateDate() == null) {
					inputTerminal.setCreateDate(new Date());
				}
				return terminalRepository.save(inputTerminal);
			}
		}
		return null;
	}

	@Override
	public List<Terminal> getTerminalsByCompany(Long id) {
		return terminalRepository.findByCompanyId(id);
	}
}
