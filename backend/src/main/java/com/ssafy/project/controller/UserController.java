package com.ssafy.project.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.project.dto.User;
import com.ssafy.project.service.UserService;

@RestController
@RequestMapping("userTest")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UserService service;

	// 회원 가입
	@PostMapping
	public ResponseEntity<User> save(User member) {
		return new ResponseEntity<User>(service.save(member), HttpStatus.OK);
	}

//	// 회원 입력
//	@RequestMapping(value = "/saveMember", method = RequestMethod.GET)
//	public ResponseEntity<User> save(HttpServletRequest req, User member) {
//		return new ResponseEntity<User>(service.save(member), HttpStatus.OK);
//	}

	// 모든 회원 조회
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<User>> getAllmembers() {
		List<User> member = service.findAll();
		return new ResponseEntity<List<User>>(member, HttpStatus.OK);
	}

	// 회원번호로 회원 조회
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })	
	public ResponseEntity<User> getMember(@PathVariable("id") Integer id) {
		Optional<User> member = service.findById(id);
		return new ResponseEntity<User>(member.get(), HttpStatus.OK);
	}

	// 회원번호로 회원 삭제
	@DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> deleteMember(@PathVariable("id") Integer id) {
		service.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	// 회원번호로 회원 수정(id로 회원을 찾아 User 객체의 name, password를 수정함)
	@PutMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<User> updateMember(@PathVariable("id") Integer id, User member) {
		service.updateById(id, member);
		return new ResponseEntity<User>(member, HttpStatus.OK);
	}
}
