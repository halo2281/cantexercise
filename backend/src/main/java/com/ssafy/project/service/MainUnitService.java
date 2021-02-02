package com.ssafy.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.dto.MainUnit;
import com.ssafy.project.dto.MainUnitList;
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
				unit.getTitle(),
				unit.getImage()
		)));
		
		return result;
	}

	// 해당 대단원의 제목
	public Optional<MainUnitTitle> findMainUnit(Long id) {
		Optional<MainUnit> unit = repo.findById(id);
		
		MainUnitTitle temp = new MainUnitTitle();
		temp.setTitle(unit.get().getTitle());
		Optional<MainUnitTitle> result = Optional.of(temp);
		
		return result;
	}
}
