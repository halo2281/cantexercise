package com.ssafy.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.project.dto.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
}
