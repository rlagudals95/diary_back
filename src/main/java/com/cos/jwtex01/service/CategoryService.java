package com.cos.jwtex01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.jwtex01.domain.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public void progress (int after_progress, long category_no) {
		if(after_progress >= 100) {
			categoryRepository.updateCategoryComplete(category_no);
		} else {
			categoryRepository.updateCategoryProgress(after_progress, category_no );
		}
	}
	
	public void subtractProgress (int after_progress, long category_no) {
		if(after_progress >= 100) {
			categoryRepository.updateCategoryComplete(category_no);
		} else {
			categoryRepository.updateCategoryProgress(after_progress, category_no );
		}
	}
	
}
