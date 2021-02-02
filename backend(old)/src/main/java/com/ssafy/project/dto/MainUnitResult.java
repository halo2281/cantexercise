package com.ssafy.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MainUnitResult {
	private Integer mainUnitId;
	
	private Integer book;
	
	private String title;
	private String intro;
	private String improvement;
	private String image;
}
