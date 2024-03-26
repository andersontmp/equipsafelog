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
	private SectorService sectorService;

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

	private void validateEmployee(Employee e) throws Exception {
		if (e.getSector() == null) {
			throw new Exception("SECTOR_REQUIRED");
		}
		if (e.getIdentity() == null) {
			throw new Exception("IDENTIFY_REQUIRED");
		}
		List<Employee> employees = employeeRepository.findBySectorCompanyIdAndIdentityIgnoreCase(e.getSector().getId(),
				e.getIdentity());
		if (employees != null && !employees.isEmpty()) {
			if (e.getId() != null) {
				if (employees.stream().filter(f -> !f.getId().equals(e.getId())).findAny().isPresent()) {
					throw new Exception("IDENTIFY_ALREADY_EXISTS");
				}
			} else {
				throw new Exception("IDENTIFY_ALREADY_EXISTS");
			}
		}
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
	public Employee saveEmployee(Employee inputEmployee) throws Exception {
		if (inputEmployee != null) {
			validateEmployee(inputEmployee);
			if (inputEmployee.getId() != null) {
				Employee employee = getEmployee(inputEmployee.getId());
				if (employee != null) {
					employee.setActive(inputEmployee.getActive());
					employee.setIdentity(inputEmployee.getIdentity());
					employee.setName(inputEmployee.getName());
					if (inputEmployee.getSector() != null && inputEmployee.getSector().getId() != null) {
						employee.setSector(sectorService.getSector(inputEmployee.getSector().getId()));
					}
					employee.setIdEmployee(saveIDEmployee(inputEmployee.getIdEmployee()));
					return employeeRepository.save(employee);
				} else {
					return null;
				}
			} else {
				if (inputEmployee.getSector() != null && inputEmployee.getSector().getId() != null) {
					inputEmployee.setSector(sectorService.getSector(inputEmployee.getSector().getId()));
				}
				inputEmployee.setIdEmployee(inputEmployee.getIdEmployee());
				return employeeRepository.save(inputEmployee);
			}
		}
		return null;
	}

	@Override
	public List<Employee> getActiveEmployeeByCompany(Long companyId) {
		return employeeRepository.findByActiveAndSectorCompanyId(true, companyId);
	}

	@Override
	public List<Employee> getEmployeeByTerminal(Long terminalId) {
		Terminal terminal = terminalService.getTerminal(terminalId);
		if (terminal != null && terminal.getSector() != null) {
			terminal.setLastCommunication(new Date());
			terminalService.saveTerminal(terminal);
			return employeeRepository.findByActiveAndSectorCompanyId(true, terminal.getSector().getId());
		}
		return null;
	}

	@Override
	public List<Employee> getEmployeeByCompany(Long companyId) {
		return employeeRepository.findBySectorCompanyId(companyId);
	}

}
