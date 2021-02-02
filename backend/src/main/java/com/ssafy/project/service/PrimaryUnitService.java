package com.ssafy.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.dto.PrimaryUnit;
import com.ssafy.project.dto.PrimaryUnitList;
import com.ssafy.project.repository.PrimaryUnitRepository;

@Service
public class PrimaryUnitService {

	@Autowired
	PrimaryUnitRepository repo;

	// 해당 대단원의 중단원 목록
	public List<PrimaryUnitList> findPrimaryUnitList(Long main) {
		List<PrimaryUnit> units = repo.findByUnitOf(main);
		
		List<PrimaryUnitList> result = new ArrayList<>();
		units.forEach(unit -> result.add(new PrimaryUnitList(
				unit.getTitle(),
				unit.getImage()
		)));
		
		return result;
	}
}
