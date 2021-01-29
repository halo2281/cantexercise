package com.ssafy.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.dto.MainUnit;
import com.ssafy.project.dto.MainUnitResult;
import com.ssafy.project.repository.MainUnitRepository;

@Service
public class MainUnitService {

	@Autowired
	MainUnitRepository repo;

	// 전체 대단원 조회
	public List<MainUnitResult> findMainUnitList() {
		List<MainUnit> units = repo.findAll();
		
		List<MainUnitResult> result = new ArrayList<>();
		units.forEach(unit -> result.add(new MainUnitResult(
				unit.getMainUnitId(),
				unit.getSb().getBookId(),
				unit.getTitle(),
				unit.getIntro(),
				unit.getImprovement(),
				unit.getImage()
		)));
		
		return result;
	}

	// 특정 대단원 조회
	public Optional<MainUnit> findMainUnit(Integer id) {
		Optional<MainUnit> unit = repo.findById(id);
		return unit;
	}
}
