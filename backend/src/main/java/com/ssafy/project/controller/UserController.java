package com.ssafy.project.controller;

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

import com.ssafy.project.dto.UserCheck;
import com.ssafy.project.dto.UserSignIn;
import com.ssafy.project.dto.UserUpdate;
import com.ssafy.project.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	// private final Logger logger = LoggerFactory.getLogger(this.getClass());

	static final int SUCCESS = 1;
	static final int FAIL = -1;
	
	@Autowired
	UserService service;

	// 로그인
//	@GetMapping
//	public ResponseEntity<User> login(User member) {
//		return new ResponseEntity<User>(service.save(member), HttpStatus.OK);
//	}
	
	// 정보 추가(회원 가입)
	@PostMapping
	public ResponseEntity<Integer> signUp(UserSignIn user) {
		if(service.insertUser(user))
			return new ResponseEntity<Integer>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<Integer>(FAIL, HttpStatus.NOT_ACCEPTABLE);
	}
	
	// 정보 조회(마이 페이지)
	@GetMapping(value="/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserCheck> myPage(@PathVariable("userId") Long id) {
		UserCheck result = service.selectUser(id);
		if(result != null)
			return new ResponseEntity<UserCheck>(result, HttpStatus.OK);
		else
			return new ResponseEntity<UserCheck>(result, HttpStatus.NOT_ACCEPTABLE);
	}
	
	// 정보 수정
	@PutMapping(value="/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Integer> editProfile(@PathVariable("userId") Long id, UserUpdate user) {
		if(service.updateUser(id, user))
			return new ResponseEntity<Integer>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<Integer>(FAIL, HttpStatus.NOT_ACCEPTABLE);
	}
	
	// 정보 삭제(회원 탈퇴)
	@DeleteMapping(value="/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Integer> signOut(@PathVariable("userId") Long id) {
		if(service.deleteUser(id))
			return new ResponseEntity<Integer>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<Integer>(FAIL, HttpStatus.NOT_ACCEPTABLE);
	}
}
