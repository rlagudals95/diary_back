package com.cos.jwtex01.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Post, Integer>{
	
}
