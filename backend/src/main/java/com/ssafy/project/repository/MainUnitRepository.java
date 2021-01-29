package com.ssafy.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.project.dto.MainUnit;

@Repository
public interface MainUnitRepository extends JpaRepository<MainUnit, Integer>{
	
}
