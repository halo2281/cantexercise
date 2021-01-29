package com.ssafy.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.dto.User;
import com.ssafy.project.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;
	
	// 회원 저장(회원가입)
	public User save(User user) { 
		repo.save(user); 
		return user; 
	}
	
	// 전체 회원 조회
	public List<User> findAll(){
		//List<User> users = new ArrayList<>();
		//repo.findAll().forEach(e->users.add(e));
		return repo.findAll();
	}
	
	// 특정 회원 조회
	public Optional<User> findById(Integer id) { 
		Optional<User> user = repo.findById(id); 
		System.out.println("findbyId 호출");
		return user; 
	} 
	
	
	public void deleteById(Integer id) { 
		repo.deleteById(id); 
	} 
	
	public void updateById(Integer id, User user) { 
		Optional<User> e = repo.findById(id); 
		if (e.isPresent()) { 
			e.get().setName(user.getName());
			repo.save(user); 
		}
		else {
			System.out.println("Can't Find Object to Update");
		}
	}
	
}