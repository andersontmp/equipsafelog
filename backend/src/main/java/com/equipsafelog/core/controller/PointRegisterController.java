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

import com.equipsafelog.core.domain.PointRegister;
import com.equipsafelog.core.service.PointRegisterService;
import com.equipsafelog.core.to.PointRegisterCriteriaSearch;
import com.equipsafelog.core.to.PointRegisterResultSearch;

@RestController
@RequestMapping("/pointregister")
public class PointRegisterController {

	@Autowired
	private PointRegisterService pointRegisterService;

	@GetMapping
	public List<PointRegister> getAllRegisters() {
		return pointRegisterService.getAllRegisters();
	}

	@GetMapping(path = "/{id}")
	public PointRegister getRegisterById(@PathVariable Long id) {
		return pointRegisterService.getRegisterById(id);
	}

	@PostMapping
	public PointRegister updatePointRegister(@RequestBody PointRegister inputPoint) {
		return pointRegisterService.savePointRegister(inputPoint);
	}

	@PostMapping(path = "/updateDate")
	public PointRegister updateDatePointRegister(@RequestBody PointRegister inputPoint) {
		return pointRegisterService.updateDatePointRegister(inputPoint);
	}

	@PostMapping(path = "/criteria")
	public List<PointRegisterResultSearch> findByCriteria(@RequestBody PointRegisterCriteriaSearch criteria) {
		return pointRegisterService.findByCriteria(criteria);
	}

	@PostMapping(path = "/employee")
	public List<PointRegister> getRegistersByEmployee(@RequestBody PointRegisterCriteriaSearch criteria) {
		return pointRegisterService.getRegistersByEmployee(criteria).parallelStream().map(f -> {
			f.getEmployee().setActive(null);
			return f;
		}).collect(Collectors.toList());
	}

	@PostMapping(path = "/inconsistency")
	public List<PointRegisterResultSearch> findInconsistencyByCriteria(
			@RequestBody PointRegisterCriteriaSearch criteria) {
		return pointRegisterService.findInconsistencyByCriteria(criteria);
	}

	@GetMapping(path = "/export/{employeeId}")
	public Object exportRegisterByEmployee(@PathVariable Long employeeId) {
		return pointRegisterService.exportRegisterByEmployee(employeeId);
	}
}
