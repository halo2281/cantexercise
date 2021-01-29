package com.ssafy.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.project.dto.User;
import com.ssafy.project.service.UserService;

@RestController
@RequestMapping("userTest")
public class UserController {

	// private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UserService service;

	// 회원 가입
	@PostMapping
	public ResponseEntity<User> save(User member) {
		return new ResponseEntity<User>(service.save(member), HttpStatus.OK);
	}
}
