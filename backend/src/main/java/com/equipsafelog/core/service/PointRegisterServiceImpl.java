package com.equipsafelog.core.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import com.equipsafelog.core.domain.Sector;
import com.equipsafelog.core.to.PointRegisterCriteriaSearch;
import com.equipsafelog.core.to.PointRegisterResultSearch;
import com.equipsafelog.repository.PointRegisterRepository;

@Service
public class PointRegisterServiceImpl implements PointRegisterService {

	@Autowired
	private PointRegisterRepository pointRegisterRepository;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private TerminalService terminalService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private SectorService sectorService;

	static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");

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
			Employee employee = employeeService.getEmployee(inputPoint.getEmployee().getId());
			if (employee == null) {
				throw new RuntimeException("Empregado inexistente");
			}
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
		List<PointRegister> findByCriteria = new ArrayList<>();
		clearHours(criteria);

		Company company = new Company();
		if (criteria.getCompanyId() != null) {
			company = companyService.getCompany(criteria.getCompanyId());
		} else if (criteria.getEmployeeId() != null) {
			Employee employee = employeeService.getEmployee(criteria.getEmployeeId());
			if (employee != null) {
				company = employee.getSector().getCompany();
			}
		}
		if (criteria.getEmployeeId() != null) {
			findByCriteria = pointRegisterRepository.findByEmployeeAndDates(criteria.getEmployeeId(),
					criteria.getStart(), criteria.getEnd());
		} else if (criteria.getCompanyId() != null) {
			findByCriteria = pointRegisterRepository.findByCriteria(true, criteria.getCompanyId(), criteria.getStart(),
					criteria.getEnd());
		}
		List<PointRegisterResultSearch> syncList = mountResult(findByCriteria, company.getId(), criteria.getStart(),
				criteria.getEnd(), company.getWeekendWork());

		List<Sector> sectors = sectorService.getSectorByCompanyId(company.getId());
		Map<Long, Sector> mapSectors = sectors.stream().collect(Collectors.toMap(Sector::getId, f -> f));
		if (mapSectors != null && !mapSectors.isEmpty()) {
			return syncList.parallelStream().filter(f -> {
				return f.getQuantity().intValue() < mapSectors.get(f.getSectorId()).getMinimalUse().intValue()
						|| f.getQuantity().intValue() > mapSectors.get(f.getSectorId()).getMaximalUse().intValue();
			}).collect(Collectors.toList());
		} else {
			return syncList;
		}
	}

	@Override
	public List<PointRegisterResultSearch> findByCriteria(PointRegisterCriteriaSearch criteria) {
		return findByCriteria(criteria, false);
	}

	private List<PointRegisterResultSearch> mountResult(List<PointRegister> findByCriteria, Long companyId,
			Calendar start, Calendar end, Boolean weekendWork) {
		Map<Employee, List<PointRegister>> collect = findByCriteria.stream()
				.collect(Collectors.groupingBy(f -> f.getEmployee()));
		List<PointRegisterResultSearch> syncList = Collections.synchronizedList(new ArrayList<>());

		ConcurrentHashMap<Employee, ConcurrentHashMap<LocalDate, PointRegisterResultSearch>> map = new ConcurrentHashMap<>();
		List<Employee> employeeByCompany = employeeService.getEmployeeByCompany(companyId);
		employeeByCompany.parallelStream().forEach(c -> {
			map.put(c, new ConcurrentHashMap<>());
			Calendar startDate = (Calendar) start.clone();
			while (startDate.before(end) && startDate.before(Calendar.getInstance())) {
				if ((weekendWork == null || !weekendWork) && (startDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
						|| startDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
					startDate.add(Calendar.DATE, 1);
					continue;
				}

				LocalDate localDate = LocalDate.of(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH) + 1,
						startDate.get(Calendar.DATE));
				PointRegisterResultSearch res = new PointRegisterResultSearch();
				Calendar calendar = Calendar.getInstance();
				calendar.clear();
				calendar.set(Calendar.YEAR, localDate.getYear());
				calendar.set(Calendar.MONTH, localDate.getMonthValue() - 1);
				calendar.set(Calendar.DAY_OF_MONTH, localDate.getDayOfMonth());

				res.setDate(calendar);
				res.setQuantity(0L);
				res.setEmployeeId(c.getId());
				res.setEmployeeIdentity(c.getIdentity());
				res.setEmployeeName(c.getName());
				res.setSectorId(c.getSector().getId());
				map.get(c).put(localDate, res);

				startDate.add(Calendar.DATE, 1);
			}
		});

		collect.entrySet().parallelStream().forEach(f -> {
			f.getValue().stream().forEach(t -> {
				LocalDate localDate = LocalDate.of(t.getDate().getYear() + 1900, t.getDate().getMonth() + 1,
						t.getDate().getDate());
				map.get(f.getKey()).get(localDate).setQuantity(map.get(f.getKey()).get(localDate).getQuantity() + 1);
			});
			syncList.addAll(map.get(f.getKey()).values());
		});
		return syncList;
	}

	private List<PointRegisterResultSearch> findByCriteria(PointRegisterCriteriaSearch criteria, Boolean weekendWork) {
		clearHours(criteria);
		List<PointRegister> findByCriteria = pointRegisterRepository.findByCriteria(true, criteria.getCompanyId(),
				criteria.getStart(), criteria.getEnd());
		return mountResult(findByCriteria, criteria.getCompanyId(), criteria.getStart(), criteria.getEnd(),
				weekendWork);
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

	@Override
	public List<String> exportRegisterByEmployee(Long employeeId) {
		List<Object[]> registers = pointRegisterRepository.findByEmployeeId(employeeId);
		List<String> result = new ArrayList<>();
		result.add("Matricula;Terminal;Data\n");
		registers.stream().forEach(f -> {
			result.add(f[0] + ";" + f[1] + ";" + sdf.format((Timestamp) f[2]) + "\n");
		});
		return result;
	}
}
