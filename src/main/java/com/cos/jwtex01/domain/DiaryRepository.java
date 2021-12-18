package com.cos.jwtex01.domain;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiaryRepository extends JpaRepository<Diary, Long>{
	
	@Query(value = "SELECT * "
			+ "FROM Diary WHERE admin_no = :admin_no  ORDER BY create_date DESC LIMIT :page, :size  ", nativeQuery = true)
	List<Map<String,Object>> findByAdmin_no(
			@Param("admin_no") Long admin_no, 
			@Param("size") Object size, 
			@Param("page") Object page);
	
	
	@Query(value = "SELECT * FROM Diary WHERE diary_no = ?1", nativeQuery = true)
	Optional <Diary> findByDiary_no(Long diary_no);
	
	
	@Query(value = "select group_concat(title, content) from Diary d where :admin_no", nativeQuery = true)
	String findKeyword(@Param("admin_no") Long admin_no);

	
	
}	
