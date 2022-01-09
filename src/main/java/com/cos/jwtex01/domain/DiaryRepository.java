package com.cos.jwtex01.domain;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiaryRepository extends JpaRepository<Diary, Long>{
	
	@Query(value = "SELECT d.diary_no,\r\n" + 
			"d.create_date,\r\n" + 
			"d.content,\r\n" + 
			"d.title,\r\n" + 
			"d.admin_no,\r\n" + 
			"d.image_url,\r\n" + 
			"c.name,\r\n" + 
			"c.category_no,\r\n" + 
			"c.complete_yn  FROM Diary d \r\n" + 
			"LEFT JOIN Category c on d.category_no = c.category_no \r\n" + 
			"WHERE d.admin_no = :admin_no  \r\n" + 
			"ORDER BY d.create_date DESC \r\n" + 
			"LIMIT :page, :size", nativeQuery = true)
	List<Map<String,Object>> findByAdmin_no(
			@Param("admin_no") Long admin_no, 
			@Param("size") Object size, 
			@Param("page") Object page);
	
	
	@Query(value = "SELECT * FROM Diary WHERE diary_no = ?1", nativeQuery = true)
	Optional <Diary> findByDiary_no(Long diary_no);
	
	
	@Query(value = "select group_concat(title, content) from Diary d where :admin_no", nativeQuery = true)
	String findKeyword(@Param("admin_no") Long admin_no);

//	@Modifying(clearAutomatically = true)
//    @Query("UPDATE Post p SET p.title = :title WHERE p.id = :id")
//    int addScoreByCategory(Long category_no, Long Score);
	
}	
