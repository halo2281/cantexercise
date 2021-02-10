package com.ssafy.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.dto.Detail;
import com.ssafy.project.dto.DetailList;
import com.ssafy.project.dto.DetailResult;
import com.ssafy.project.dto.DetailTitle;
import com.ssafy.project.repository.DetailRepository;

@Service
public class DetailService {
	
	@Autowired
	DetailRepository repo;
	
	// 전체 테이블 조회
	public List<Detail> findDetail(){		
		return repo.findAll();
	}
	
	// 소단원의 세부내용목록
	public List<DetailList> findDetailList(Long unit){
		List<Detail> detailList = repo.findByUnitOf(unit);
		List<DetailList> result = new ArrayList<>();
		detailList.forEach(e -> result.add(new DetailList(
					e.getDetailId(),
					e.getTitle(),
					e.getGuideImage()
				)));
		
		return result;
	}
	
	// 세부내용의 내용
	public Optional<DetailResult> findDetail(Long id) {
		Optional<Detail> unit = repo.findById(id);
		
		DetailResult temp = new DetailResult(
				unit.get().getDetailId(),
				unit.get().getUnit().getSubUnitId(),
				unit.get().getTitle(),
				unit.get().getPosture(),
				unit.get().getObjective(),
				unit.get().getTip(),
				unit.get().getGuideImage(),
				unit.get().getIotManual(),
				unit.get().getIotManualImage(),
				unit.get().getIotWearImage(),
				unit.get().getIotExerciseImage(),
				unit.get().getIotPracticeImage()
			);
		
		Optional<DetailResult> result = Optional.of(temp);
		
		return result;
	}
	
	public Optional<DetailTitle> findDetailTitle(Long id) {
		Optional<Detail> unit = repo.findById(id);
		
		DetailTitle temp = new DetailTitle(unit.get().getDetailId(), unit.get().getTitle());
		Optional<DetailTitle> result = Optional.of(temp);
		return result;
	}
}
