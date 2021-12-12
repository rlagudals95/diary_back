package com.cos.jwtex01.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
	User findByUsername(String username);
}
