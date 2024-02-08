package com.equipsafelog.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipsafelog.core.domain.Employee;
import com.equipsafelog.core.domain.PointRegister;
import com.equipsafelog.repository.EmployeeRepository;
import com.equipsafelog.repository.PointRegisterRepository;

@RestController
@RequestMapping("/pointregister")
public class PointRegisterController {

	@Autowired
	private PointRegisterRepository pointRegisterRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping
	public List<PointRegister> getAllRegisters() {
		return pointRegisterRepository.findAll();
	}

	@PostMapping
	public PointRegister updatePointRegister(@RequestBody PointRegister inputPoint) {
		if (inputPoint != null && inputPoint.getEmployee() != null) {
			Employee employee = employeeRepository.findById(inputPoint.getEmployee().getId())
					.orElseThrow(() -> new RuntimeException("Empregado inexistente"));
			PointRegister point = new PointRegister();
			point.setEmployee(employee);
			point.setTerminalId(inputPoint.getTerminalId());
			point.setDate(inputPoint.getDate());
			return pointRegisterRepository.save(point);
		} else {
			throw new RuntimeException("Empregado inexistente");
		}

	}
}
