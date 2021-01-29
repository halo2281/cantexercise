package com.ssafy.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.dto.SubUnit;
import com.ssafy.project.repository.SubUnitRepository;

@Service
public class SubUnitService {

	@Autowired
	SubUnitRepository repo;
	
	public List<SubUnit> findSubUnitList(Integer primary){
		List<SubUnit> unitList = repo.findByUnitOf(primary);
		return unitList;
	}
}
