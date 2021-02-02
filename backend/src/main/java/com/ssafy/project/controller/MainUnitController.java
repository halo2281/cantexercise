package com.ssafy.project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.project.dto.MainUnitList;
import com.ssafy.project.dto.MainUnitTitle;
import com.ssafy.project.service.MainUnitService;

@RestController
@RequestMapping("mainUnit")
public class MainUnitController {

	// private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MainUnitService service;

	// 전체 대단원 목록 조회
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<MainUnitList>> getMainUnitList() {
		List<MainUnitList> list = service.findMainUnitList();
		if (list == null) {
			return new ResponseEntity<List<MainUnitList>>(new ArrayList<MainUnitList>(), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<MainUnitList>>(list, HttpStatus.OK);
		}
	}
	
	// 해당 대단원 제목
	@GetMapping(value="/{mainId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MainUnitTitle> getMainUnitTitle(@PathVariable("mainId") Long id) {
		Optional<MainUnitTitle> unit = service.findMainUnit(id);
		return new ResponseEntity<MainUnitTitle>(unit.get(), HttpStatus.OK);

	}

}
