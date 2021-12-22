package com.cos.jwtex01.domain;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	@Query(value = "SELECT * "
			+ "FROM Category WHERE admin_no = :admin_no  ORDER BY create_date DESC ", nativeQuery = true)
	List<Category> findByAdmin_no(@Param("admin_no") Long admin_no);
		
}	
