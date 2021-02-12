package com.ssafy.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.dto.MainUnit;
import com.ssafy.project.dto.MainUnitList;
import com.ssafy.project.dto.MainUnitResult;
import com.ssafy.project.dto.MainUnitTitle;
import com.ssafy.project.repository.MainUnitRepository;

@Service
public class MainUnitService {

	@Autowired
	MainUnitRepository repo;

	// 전체 대단원 목록 조회
	public List<MainUnitList> findMainUnitList() {
		List<MainUnit> units = repo.findAll();
		
		List<MainUnitList> result = new ArrayList<>();
		units.forEach(unit -> result.add(new MainUnitList(
				unit.getMainUnitId(),
				unit.getTitle(),
				unit.getImage()
		)));
		
		return result;
	}

	// 해당 대단원의 제목
	public Optional<MainUnitTitle> findMainUnitTitle(Long id) {
		Optional<MainUnit> unit = repo.findById(id);
		
		MainUnitTitle temp = new MainUnitTitle(unit.get().getMainUnitId(), unit.get().getTitle());
		Optional<MainUnitTitle> result = Optional.of(temp);
		
		return result;
	}
	
	// 해당 대단원의 전체 내용
	public Optional<MainUnitResult> findMainUnit(Long id) {
		Optional<MainUnit> unit = repo.findById(id);
		
		MainUnitResult temp = new MainUnitResult(
				unit.get().getMainUnitId(), 
				unit.get().getSb().getBookId(), 
				unit.get().getTitle(), 
				unit.get().getIntro(),
				unit.get().getImprovement(),
				unit.get().getImage()
			);
		
		Optional<MainUnitResult> result = Optional.of(temp);
		
		return result;
	}
}
