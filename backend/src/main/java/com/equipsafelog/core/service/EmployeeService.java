package com.equipsafelog.core.service;

import java.util.List;

import com.equipsafelog.core.domain.Employee;

public interface EmployeeService {

	List<Employee> getAllEmployees();

	Employee getEmployee(Long id);

	Employee saveEmployee(Employee inputEmployee) throws Exception;

	List<Employee> getEmployeeByTerminal(Long terminalId);

	List<Employee> getActiveEmployeeByCompany(Long companyId);

	List<Employee> getEmployeeByCompany(Long companyId);

}
