package com.equipsafelog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.equipsafelog.core.domain.IDEmployee;

public interface IDEmployeeRepository extends JpaRepository<IDEmployee, Long> {
}
