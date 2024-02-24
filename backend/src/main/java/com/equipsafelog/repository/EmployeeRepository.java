package com.equipsafelog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.equipsafelog.core.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query
	public List<Employee> findByActiveAndCompanyId(Boolean active, Long companyId);

	@Query
	public List<Employee> findByCompanyId(Long companyId);
	
	@Query
	public List<Employee> findByCompanyIdAndIdentityIgnoreCase(Long companyId, String identity);
}
