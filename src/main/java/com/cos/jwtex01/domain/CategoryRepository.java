package com.cos.jwtex01.domain;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	@Query(value = "SELECT "
			+ "c.category_no, "
			+ "c.complete_yn, "
			+ "c.progress, "
			+ "c.name, "
			+ "c.image_url, "
			+ "c.use_yn, "
			+ "DATE_FORMAT(c.create_date, '%Y-%m-%d %p %h:%i' ) as create_date "
			+ "FROM Category c "
			+ "WHERE c.admin_no = :admin_no "
			+ "AND c.complete_yn = :complete_yn "
			+ "AND c.use_yn = 'Y' ORDER BY c.create_date DESC ", nativeQuery = true)
	List<Map<String,Object>> findByAdmin_no(@Param("admin_no") Long admin_no, @Param("complete_yn") String complety_yn);
	
	@Query(value = "SELECT c.progress "
			+ "FROM Category c WHERE c.category_no = :category_no", nativeQuery = true)
	int findByCategory_no(@Param("category_no") Long category_no);
	
	// 완료 되지 않은 카테고리 수 제한 10개
	@Query(value = "select * from Category where admin_no = :admin_no AND complete_yn != 'Y'", nativeQuery = true)
	List<Map<String, Object>> findNoCompleCategory(@Param("admin_no") Long admin_no);
	
	@Query(value = "SELECT * "
			+ "FROM Category c WHERE c.category_no = :category_no", nativeQuery = true)
	Category findCategory(@Param("category_no") Long category_no);
	
	@Transactional
	@Modifying(clearAutomatically = true)
    @Query(value="UPDATE Category c SET c.progress = :after_progress WHERE c.category_no = :category_no", nativeQuery = true)
    int updateCategoryProgress(int after_progress, Long category_no);
	
	@Transactional
	@Modifying(clearAutomatically = true)
    @Query(value="UPDATE Category c SET c.progress = :progress WHERE c.category_no = :category_no", nativeQuery = true)
	void updateCategoryProgress(Long category_no, int progress);
	
	@Transactional
	@Modifying(clearAutomatically = true)
    @Query(value="UPDATE Category c SET c.complete_yn = 'Y', c.progress = 100 WHERE c.category_no = :category_no", nativeQuery = true)
    int updateCategoryComplete(Long category_no);
	
	@Transactional
	@Modifying(clearAutomatically = true)
    @Query(value="UPDATE Category c SET c.use_yn = 'N' WHERE c.category_no = :category_no", nativeQuery = true)
    void updateCategoryUse(Long category_no);
	
}	
