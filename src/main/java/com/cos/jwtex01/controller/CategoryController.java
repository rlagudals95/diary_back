package com.cos.jwtex01.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.jwtex01.config.auth.LoginUser;
import com.cos.jwtex01.config.auth.Principal;
import com.cos.jwtex01.domain.Category;
import com.cos.jwtex01.domain.CategoryRepository;
import com.cos.jwtex01.domain.Diary;
import com.cos.jwtex01.domain.DiaryRepository;
import com.cos.jwtex01.dto.CategoryReqDto;
import com.cos.jwtex01.dto.DiaryReqDto;
import com.cos.jwtex01.service.GrammarService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {
	
	private final CategoryRepository categoryRepository;
	
	@PostMapping("/list")
	public List<Category> main(@LoginUser Principal principal, @RequestBody Map<String, Object > param) {
		System.out.println("완료여부 : " + param);
		return categoryRepository.findByAdmin_no(principal.getUser().getAdmin_no(),(String) param.get("complete_yn"));
	}
	
	@PostMapping("/add")
	public Category add(@LoginUser Principal principal, @RequestBody CategoryReqDto categoryReqDto) {
		System.out.println("categoryReqDto : "+categoryReqDto);
		System.out.println("principal : "+principal.getUser());
		return categoryRepository.save(categoryReqDto.toEntity(principal.getUser()));	
	}
	
	@PostMapping("/view/{id}")
	public Category view(@PathVariable Long id) {
		return categoryRepository.findCategory(id);
	}
	
	@PostMapping("/edit/{id}")
	public Category edit(@PathVariable Long id, @RequestBody Map<String, Object> param) {
		int progress =  (int) param.get("progress");
		return categoryRepository.updateCategoryProgress(id,progress);
	}
}
