package com.equipsafelog.core.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipsafelog.core.domain.Employee;
import com.equipsafelog.core.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();
	}

	@GetMapping(path = "/{id}")
	public Employee getEmployee(@PathVariable Long id) {
		return employeeService.getEmployee(id);
	}

	@PostMapping
	public Employee saveEmployee(@RequestBody Employee inputEmployee) {
		return employeeService.saveEmployee(inputEmployee);
	}

	@GetMapping(path = "/company/{companyId}/{loadAllData}")
	public List<Employee> getEmployeeByCompany(@PathVariable Long companyId, @PathVariable Boolean loadAllData) {
		List<Employee> employeeByCompany = employeeService.getEmployeeByCompany(companyId);
		if (!loadAllData) {
			return employeeByCompany.parallelStream().map(f -> {
				f.setCompany(null);
				f.setActive(null);
				return f;
			}).collect(Collectors.toList());
		}
		return employeeByCompany;
	}

	@GetMapping(path = "/terminal/{terminalId}")
	public List<Employee> getEmployeeByTerminal(@PathVariable Long terminalId) {
		return employeeService.getEmployeeByTerminal(terminalId).parallelStream().map(f -> {
			f.setCompany(null);
			f.setActive(null);
			return f;
		}).collect(Collectors.toList());
	}

	@GetMapping(path = "/company/active/{companyId}")
	public List<Employee> getActiveEmployeeByCompany(@PathVariable Long companyId) {
		return employeeService.getActiveEmployeeByCompany(companyId).parallelStream().map(f -> {
			f.setCompany(null);
			f.setActive(null);
			return f;
		}).collect(Collectors.toList());
	}
}
