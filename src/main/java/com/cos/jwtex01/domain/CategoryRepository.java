package com.cos.jwtex01.domain;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	@Query(value = "SELECT * "
			+ "FROM Category c WHERE c.admin_no = :admin_no  ORDER BY c.create_date DESC ", nativeQuery = true)
	List<Category> findByAdmin_no(@Param("admin_no") Long admin_no);
	
	@Query(value = "SELECT c.progress "
			+ "FROM Category c WHERE c.category_no = :category_no", nativeQuery = true)
	int findByCategory_no(@Param("category_no") Long category_no);
	
	@Transactional
	@Modifying(clearAutomatically = true)
    @Query(value="UPDATE Category c SET c.progress = :after_progress WHERE c.category_no = :category_no", nativeQuery = true)
    int updateCategoryProgress(int after_progress, Long category_no);
	
	@Transactional
	@Modifying(clearAutomatically = true)
    @Query(value="UPDATE Category c SET c.complete_yn = 'Y' WHERE c.category_no = :category_no", nativeQuery = true)
    int updateCategoryComplete(Long category_no);

}	
