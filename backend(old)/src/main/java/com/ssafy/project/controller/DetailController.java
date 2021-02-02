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

import com.ssafy.project.dto.Detail;
import com.ssafy.project.service.DetailService;

@RestController
@RequestMapping("detail")
public class DetailController {

	@Autowired
	DetailService service;

	// 특정 대단원의 중단원 목록 조회
	@GetMapping(value="/{subId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Detail>> getDetailList(@PathVariable("subId") Integer subId) {
		List<Detail> list = service.findDetailList(subId);
		if (list == null) {
			return new ResponseEntity<List<Detail>>(new ArrayList<Detail>(), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Detail>>(list, HttpStatus.OK);
		}
	}
}
