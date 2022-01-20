package com.cos.jwtex01.domain;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer>{
	User findByUsername(String username);
	
	@Transactional
	@Modifying(clearAutomatically = true)
    @Query(value="UPDATE User u SET u.birthday = :birthday WHERE u.username = :username", nativeQuery = true)
	void setBirthday(String username, String birthday);
}
