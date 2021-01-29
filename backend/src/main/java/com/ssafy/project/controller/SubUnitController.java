package com.ssafy.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.project.dto.SubUnit;
import com.ssafy.project.service.SubUnitService;

@RestController
@RequestMapping("subUnit")
public class SubUnitController {

	@Autowired
	SubUnitService service;

	// 특정 대단원의 중단원 목록 조회
	@GetMapping(value="/{primaryId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<SubUnit>> getSubUnitList(@PathVariable("primaryId") Integer primaryId) {
		List<SubUnit> list = service.findSubUnitList(primaryId);
		if (list == null) {
			return new ResponseEntity<List<SubUnit>>(new ArrayList<SubUnit>(), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<SubUnit>>(list, HttpStatus.OK);
		}
	}
}
