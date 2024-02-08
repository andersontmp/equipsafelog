package com.equipsafelog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.equipsafelog.core.domain.PointRegister;

public interface PointRegisterRepository extends JpaRepository<PointRegister, Long> {
}

