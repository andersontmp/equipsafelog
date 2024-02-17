package com.equipsafelog.core.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equipsafelog.core.domain.Employee;
import com.equipsafelog.core.domain.IDEmployee;
import com.equipsafelog.core.domain.Terminal;
import com.equipsafelog.repository.EmployeeRepository;
import com.equipsafelog.repository.IDEmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private IDEmployeeRepository idEmployeeRepository;

	@Autowired
	private TerminalService terminalService;

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployee(Long id) {
		return employeeRepository.findById(id).orElse(null);
	}

	private IDEmployee saveIDEmployee(IDEmployee idEmployee) {
		if (idEmployee != null) {
			String identity = idEmployee.getIdentity();
			if (idEmployee.getId() != null) {
				idEmployee = idEmployeeRepository.findById(idEmployee.getId()).orElse(null);
			}
			if (idEmployee != null) {
				idEmployee.setIdentity(identity);
				return idEmployeeRepository.save(idEmployee);
			}
		}
		return null;
	}

	@Override
	public Employee saveEmployee(Employee inputEmployee) {
		if (inputEmployee != null) {
			if (inputEmployee.getId() != null) {
				Employee employee = getEmployee(inputEmployee.getId());
				if (employee != null) {
					employee.setActive(inputEmployee.getActive());
					employee.setIdentity(inputEmployee.getIdentity());
					if (inputEmployee.getCompany() != null && inputEmployee.getCompany().getId() != null) {
						employee.setCompany(companyService.getCompany(inputEmployee.getCompany().getId()));
					}
					employee.setIdEmployee(saveIDEmployee(inputEmployee.getIdEmployee()));
					return employeeRepository.save(employee);
				} else {
					return null;
				}
			} else {
				if (inputEmployee.getCompany() != null && inputEmployee.getCompany().getId() != null) {
					inputEmployee.setCompany(companyService.getCompany(inputEmployee.getCompany().getId()));
				}
				inputEmployee.setIdEmployee(inputEmployee.getIdEmployee());
				return employeeRepository.save(inputEmployee);
			}
		}
		return null;
	}

	@Override
	public List<Employee> getActiveEmployeeByCompany(Long companyId) {
		return employeeRepository.findByActiveAndCompanyId(true, companyId);
	}

	@Override
	public List<Employee> getEmployeeByTerminal(Long terminalId) {
		Terminal terminal = terminalService.getTerminal(terminalId);
		if (terminal != null && terminal.getCompany() != null) {
			terminal.setLastCommunication(new Date());
			terminalService.saveTerminal(terminal);
			return employeeRepository.findByActiveAndCompanyId(true, terminal.getCompany().getId());
		}
		return null;
	}
	
	@Override
	public List<Employee> getEmployeeByCompany(Long companyId) {
		return employeeRepository.findByCompanyId(companyId);
	}

}
