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

import com.ssafy.project.dto.PrimaryUnitList;
import com.ssafy.project.service.PrimaryUnitService;

@RestController
@RequestMapping("primaryUnit")
public class PrimaryUnitController {

	@Autowired
	PrimaryUnitService service;

	// 해당 대단원의 중단원 목록
	@GetMapping(value="/{mainId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<PrimaryUnitList>> getPrimaryUnitList(@PathVariable("mainId") Long mainId) {
		List<PrimaryUnitList> list = service.findPrimaryUnitList(mainId);
		if (list == null) {
			return new ResponseEntity<List<PrimaryUnitList>>(new ArrayList<PrimaryUnitList>(), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<PrimaryUnitList>>(list, HttpStatus.OK);
		}
	}
}
