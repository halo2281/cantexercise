package com.ssafy.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.dto.PrimaryUnit;
import com.ssafy.project.dto.PrimaryUnitResult;
import com.ssafy.project.repository.PrimaryUnitRepository;

@Service
public class PrimaryUnitService {

	@Autowired
	PrimaryUnitRepository repo;

	// 특정 대단원의 중단원 목록 조회
	public List<PrimaryUnitResult> findPrimaryUnitList(Integer main) {
		List<PrimaryUnit> units = repo.findByUnitOf(main);
		
		List<PrimaryUnitResult> result = new ArrayList<>();
		units.forEach(unit -> result.add(new PrimaryUnitResult(
				unit.getPriUnitId(),
				unit.getMain().getMainUnitId(),
				unit.getTitle(),
				unit.getImage()
		)));
		
		return result;
	}
}
