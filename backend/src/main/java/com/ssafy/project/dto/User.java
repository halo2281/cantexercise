package com.ssafy.project.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class User {

	@Id
	@Column(name = "userId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String loginId;    // 유저 아이디
	private String password;   // 유저 비밀번호
	private String email;      // 유저 이메일
	private String school;     // 학교
	private String userType;   // 유저 분류(1-교사, 2-학생)
	private Long grade;        // 학년
	private Long height;       // 키
	private Long armLength;    // 팔 길이
	private Long legLength;    // 다리 길이

}
