package com.equipsafelog.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.equipsafelog.core.domain.PointRegister;

public interface PointRegisterRepository extends JpaRepository<PointRegister, Long> {

	@Query(value = "SELECT t FROM PointRegister t WHERE t.employee.active = ?1 AND t.employee.company.id = ?2 AND t.date BETWEEN ?3 AND ?4")
	List<PointRegister> findByCriteria(Boolean active, Long companyId, Calendar start, Calendar end);
	
	@Query(value = "SELECT t FROM PointRegister t WHERE t.employee.id = ?1 AND t.date BETWEEN ?2 AND ?3")
	List<PointRegister> findByEmployeeAndDates(Long employeeId, Calendar start, Calendar end);
}
