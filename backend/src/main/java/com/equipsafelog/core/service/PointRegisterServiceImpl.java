package com.equipsafelog.core.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.equipsafelog.core.domain.Company;
import com.equipsafelog.core.domain.Employee;
import com.equipsafelog.core.domain.PointRegister;
import com.equipsafelog.core.to.PointRegisterCriteriaSearch;
import com.equipsafelog.core.to.PointRegisterResultSearch;
import com.equipsafelog.repository.EmployeeRepository;
import com.equipsafelog.repository.PointRegisterRepository;

@Service
public class PointRegisterServiceImpl implements PointRegisterService {

	@Autowired
	private PointRegisterRepository pointRegisterRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private TerminalService terminalService;

	@Autowired
	private CompanyService companyService;

	@Override
	public List<PointRegister> getAllRegisters() {
		return pointRegisterRepository.findAll();
	}

	@Override
	public PointRegister getRegisterById(Long registerId) {
		return pointRegisterRepository.findById(registerId).orElse(null);
	}

	@Override
	public PointRegister savePointRegister(@RequestBody PointRegister inputPoint) {
		if (inputPoint != null && inputPoint.getEmployee() != null) {
			Employee employee = employeeRepository.findById(inputPoint.getEmployee().getId())
					.orElseThrow(() -> new RuntimeException("Empregado inexistente"));
			PointRegister point = new PointRegister();
			point.setEmployee(employee);
			if (inputPoint.getTerminal() != null && inputPoint.getTerminal().getId() != null) {
				point.setTerminal(terminalService.getTerminal(inputPoint.getTerminal().getId()));
			}
			point.setDate(inputPoint.getDate());
			return pointRegisterRepository.save(point);
		} else {
			throw new RuntimeException("Empregado inexistente");
		}
	}

	private void clearHours(PointRegisterCriteriaSearch criteria) {
		if (criteria.getStart() != null) {
			criteria.getStart().set(Calendar.HOUR_OF_DAY, 0);
			criteria.getStart().set(Calendar.MINUTE, 0);
			criteria.getStart().set(Calendar.SECOND, 0);
		}
		if (criteria.getEnd() != null) {
			criteria.getEnd().set(Calendar.HOUR_OF_DAY, 23);
			criteria.getEnd().set(Calendar.MINUTE, 59);
			criteria.getEnd().set(Calendar.SECOND, 59);
		}
	}

	@Override
	public List<PointRegisterResultSearch> findInconsistencyByCriteria(PointRegisterCriteriaSearch criteria) {
		Company company = companyService.getCompany(criteria.getCompanyId());
		List<PointRegister> findByCriteria = pointRegisterRepository.findByCriteria(true, criteria.getCompanyId(),
				criteria.getStart(), criteria.getEnd());
		Map<Employee, List<PointRegister>> collect = findByCriteria.stream()
				.collect(Collectors.groupingBy(f -> f.getEmployee()));
		List<PointRegisterResultSearch> syncList = Collections.synchronizedList(new ArrayList<>());

		collect.entrySet().parallelStream().forEach(f -> {
			ConcurrentHashMap<LocalDate, PointRegisterResultSearch> hashByDate = new ConcurrentHashMap<>();
			f.getValue().stream().forEach(t -> {
				LocalDate localDate = LocalDate.of(t.getDate().getYear() + 1900, t.getDate().getMonth() + 1,
						t.getDate().getDate());
				if (!hashByDate.containsKey(localDate)) {
					PointRegisterResultSearch res = new PointRegisterResultSearch();

					Calendar calendar = Calendar.getInstance();
					calendar.clear();
					calendar.set(Calendar.YEAR, localDate.getYear());
					calendar.set(Calendar.MONTH, localDate.getMonthValue() - 1);
					calendar.set(Calendar.DAY_OF_MONTH, localDate.getDayOfMonth());

					res.setDate(calendar);
					res.setQuantity(0L);
					res.setEmployeeId(f.getKey().getId());
					res.setEmployeeIdentity(f.getKey().getIdentity());
					hashByDate.put(localDate, res);
				}
				hashByDate.get(localDate).setQuantity(hashByDate.get(localDate).getQuantity() + 1);
			});
			syncList.addAll(hashByDate.values());
		});
		if (company.getMinimalUse() != null && company.getMaximalUse() != null) {
			return syncList.parallelStream().filter(f -> {
				return f.getQuantity().intValue() < company.getMinimalUse().intValue()
						|| f.getQuantity().intValue() > company.getMaximalUse().intValue();
			}).collect(Collectors.toList());
		} else {
			return syncList;
		}
	}

	@Override
	public List<PointRegisterResultSearch> findByCriteria(PointRegisterCriteriaSearch criteria) {
		clearHours(criteria);
		List<PointRegister> findByCriteria = pointRegisterRepository.findByCriteria(true, criteria.getCompanyId(),
				criteria.getStart(), criteria.getEnd());
		Map<Employee, List<PointRegister>> collect = findByCriteria.stream()
				.collect(Collectors.groupingBy(f -> f.getEmployee()));
		List<PointRegisterResultSearch> syncList = Collections.synchronizedList(new ArrayList<>());

		collect.entrySet().parallelStream().forEach(f -> {
			ConcurrentHashMap<LocalDate, PointRegisterResultSearch> hashByDate = new ConcurrentHashMap<>();
			f.getValue().stream().forEach(t -> {
				LocalDate localDate = LocalDate.of(t.getDate().getYear() + 1900, t.getDate().getMonth() + 1,
						t.getDate().getDate());
				if (!hashByDate.containsKey(localDate)) {
					PointRegisterResultSearch res = new PointRegisterResultSearch();

					Calendar calendar = Calendar.getInstance();
					calendar.clear();
					calendar.set(Calendar.YEAR, localDate.getYear());
					calendar.set(Calendar.MONTH, localDate.getMonthValue() - 1);
					calendar.set(Calendar.DAY_OF_MONTH, localDate.getDayOfMonth());

					res.setDate(calendar);
					res.setQuantity(0L);
					res.setEmployeeId(f.getKey().getId());
					res.setEmployeeIdentity(f.getKey().getIdentity());
					hashByDate.put(localDate, res);
				}
				hashByDate.get(localDate).setQuantity(hashByDate.get(localDate).getQuantity() + 1);
			});
			syncList.addAll(hashByDate.values());
		});
		return syncList;
	}

	@Override
	public List<PointRegister> getRegistersByEmployee(PointRegisterCriteriaSearch criteria) {
		clearHours(criteria);
		return pointRegisterRepository.findByEmployeeAndDates(criteria.getEmployeeId(), criteria.getStart(),
				criteria.getEnd());
	}

	@Override
	public PointRegister updateDatePointRegister(PointRegister inputPoint) {
		PointRegister pointRegister = pointRegisterRepository.findById(inputPoint.getId()).orElse(null);
		if (pointRegister != null) {
			pointRegister.setDate(inputPoint.getDate());
			return pointRegisterRepository.save(pointRegister);
		}
		return null;
	}
}
