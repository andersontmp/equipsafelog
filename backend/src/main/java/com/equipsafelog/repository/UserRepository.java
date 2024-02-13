package com.equipsafelog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.equipsafelog.core.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query
	public User findByUsername(String username);
	
}

