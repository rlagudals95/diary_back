package com.cos.jwtex01.controller;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RestController;

import com.cos.jwtex01.config.auth.LoginUser;
import com.cos.jwtex01.config.auth.Principal;
import com.cos.jwtex01.domain.Diary;
import com.cos.jwtex01.domain.DiaryRepository;
import com.cos.jwtex01.dto.DiaryReqDto;
import com.cos.jwtex01.service.GrammarService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
@RequestMapping("/diary")
public class DiaryController {
	
	@Autowired
	private GrammarService grammarService;
	
	private final DiaryRepository diaryRepository;
	
	@PostMapping("/main")
	public String main(@LoginUser Principal principal) {
		
		return (String) diaryRepository.findKeyword(principal.getUser().getAdmin_no());
	}
	
	
	@PostMapping("/post")
	public Diary post(@RequestBody DiaryReqDto diaryReqDto , @LoginUser Principal principal ) {
		return diaryRepository.save(diaryReqDto.toEntity(principal.getUser()));	
	}
	
	// 유저 리스트 
	@PostMapping("/list")
	public List<Map<String, Object>> list(@LoginUser Principal principal, @RequestBody Map<String, Object> param) {
		//Pageable paging = PageRequest.of(0, 10, Sort.Direction.DESC, "create_date");
		
		System.out.println("페이징 : "+ param);
		System.out.println("어드민 넘qj : "+principal.getUser().getAdmin_no());
		return diaryRepository.findByAdmin_no(principal.getUser().getAdmin_no(), param.get("size"), param.get("page"));
	}
	
	
	@GetMapping("/detail/{id}")
	public Optional <Diary> datail(@PathVariable Long id ) {
	
		return  diaryRepository.findByDiary_no(id);
	}
	
	@GetMapping("/list/all")
	public List<Diary> listAll() {
		List<Diary> diaryList = diaryRepository.findAll();
		return diaryList;
	}
	
	@GetMapping("/findByJpa/{id}")
	public Optional <Diary> findByJpa(@PathVariable Long id) {
		Optional <Diary> diary = diaryRepository.findById(id);
		
		return diary;
	}
	
	@PostMapping("/detail/{id}")
	public Optional<Diary> deatil(@PathVariable Long id) {
		Optional<Diary> diary = diaryRepository.findById(id);
		return diary;
	}
	
	@PostMapping("/grammar")
	public String grammar(@RequestBody Map<String, Object> param) {
		System.out.println("검사할 문자 : " +param);
		return grammarService.grammarCorrect((String)param.get("content"));
	}
	
	
	
}
