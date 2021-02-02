package com.ssafy.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailResult {
	
	private Integer detailId;
	
	private Integer unit;
	
	private String title;
	private String posture;				// 소단원 이름
	private String objective;			// 대표 이미지
	private String tip;
	private String guideImage;
	private String iotManual;
	private String iotImage;
}	
