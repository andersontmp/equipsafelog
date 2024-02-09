package com.equipsafelog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.equipsafelog.core.domain.Terminal;

public interface TerminalRepository extends JpaRepository<Terminal, Long> {
}

