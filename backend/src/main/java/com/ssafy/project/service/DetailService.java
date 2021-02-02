package com.ssafy.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.dto.Detail;
import com.ssafy.project.repository.DetailRepository;

@Service
public class DetailService {
	
	@Autowired
	DetailRepository repo;
	
	public List<Detail> findDetailList(Integer unit){
		List<Detail> detailList = repo.findByUnitOf(unit);
		return detailList;
	}
}
