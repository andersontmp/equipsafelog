package com.equipsafelog.core.service;

import java.util.List;

import com.equipsafelog.core.domain.Terminal;

public interface TerminalService {

	List<Terminal> getAllTerminals();

	Terminal getTerminal(Long id);

	Terminal saveTerminal(Terminal inputTerminal);

	List<Terminal> getTerminalsByCompany(Long id);

}
