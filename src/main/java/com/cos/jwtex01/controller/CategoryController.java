package com.cos.jwtex01.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cos.jwtex01.config.auth.LoginUser;
import com.cos.jwtex01.config.auth.Principal;
import com.cos.jwtex01.domain.Category;
import com.cos.jwtex01.domain.CategoryRepository;
import com.cos.jwtex01.domain.Diary;
import com.cos.jwtex01.domain.DiaryRepository;
import com.cos.jwtex01.dto.CategoryReqDto;
import com.cos.jwtex01.dto.DiaryReqDto;
import com.cos.jwtex01.service.AWSservice;
import com.cos.jwtex01.service.GrammarService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private AWSservice awsService;
	
	private final CategoryRepository categoryRepository;
	
	@PostMapping("/list")
	public List<Category> main(@LoginUser Principal principal, @RequestBody Map<String, Object > param) {
		System.out.println("완료여부 : " + param);
		return categoryRepository.findByAdmin_no(principal.getUser().getAdmin_no(),(String) param.get("complete_yn"));
	}
	
	@PostMapping("/add")
	public Category add(
			@LoginUser Principal principal,
			@RequestPart(value = "key") CategoryReqDto categoryReqDto,
			@RequestPart(value = "file") MultipartFile[] file) throws IOException {
		System.out.println("categoryReqDto : "+categoryReqDto);
		System.out.println("principal : "+principal.getUser());
		System.out.println("파일 : "+ file);
		System.out.println("유알엘 리턴 : " + awsService.uploadFile(file));
		
		if (awsService.uploadFile(file) != null) {
			categoryReqDto.setImage_url(awsService.uploadFile(file));		
		}
		categoryReqDto.setCategory_role("1");
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
	
	// 카테고리 삭제
	@PostMapping("/use/{id}")
	public void delete(@PathVariable Long id ) {
		categoryRepository.updateCategoryUse(id);	
	}
	
	// 카테고리 완료처리
	@PostMapping("/complete/{id}")
	public void complete(@PathVariable Long id) {
		categoryRepository.updateCategoryComplete(id);
	}
	
}
