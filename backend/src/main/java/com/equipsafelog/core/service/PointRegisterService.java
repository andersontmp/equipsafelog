package com.equipsafelog.core.service;

import java.util.List;

import com.equipsafelog.core.domain.PointRegister;
import com.equipsafelog.core.to.PointRegisterCriteriaSearch;
import com.equipsafelog.core.to.PointRegisterResultSearch;

public interface PointRegisterService {

	PointRegister savePointRegister(PointRegister inputPoint);

	List<PointRegister> getAllRegisters();

	List<PointRegisterResultSearch> findByCriteria(PointRegisterCriteriaSearch criteria);

	List<PointRegister> getRegistersByEmployee(PointRegisterCriteriaSearch criteria);

	PointRegister getRegisterById(Long registerId);

	PointRegister updateDatePointRegister(PointRegister inputPoint);

	List<PointRegisterResultSearch> findInconsistencyByCriteria(PointRegisterCriteriaSearch criteria);

}
