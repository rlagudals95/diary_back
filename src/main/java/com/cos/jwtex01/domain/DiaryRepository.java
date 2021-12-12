package com.cos.jwtex01.domain;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiaryRepository extends JpaRepository<Diary, Integer>{
	
	@Query(value = "SELECT * FROM Diary WHERE admin_no = ?1", nativeQuery = true)
	List<Map<String,Object>> findByAdmin_no(Integer admin_no);
	
	//@Query(value = "SELECT * FROM Diary WHERE admin_no = ?0", nativeQuery = true)
	//List<Map<String, Object>> findByAdmin_no(User user);

	
}	
