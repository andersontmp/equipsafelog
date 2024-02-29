package com.equipsafelog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.equipsafelog.core.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query
	public List<Employee> findByActiveAndSectorCompanyId(Boolean active, Long companyId);

	@Query
	public List<Employee> findBySectorCompanyId(Long companyId);
	
	@Query
	public List<Employee> findBySectorCompanyIdAndIdentityIgnoreCase(Long companyId, String identity);
}
