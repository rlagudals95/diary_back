package com.cos.jwtex01.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/diary")
public class DiaryController {
	
	private final DiaryRepository diaryRepository;
	
	@PostMapping("/post")
	public Diary post(@RequestBody DiaryReqDto diaryReqDto , @LoginUser Principal principal ) {
		System.out.println("다이어리 내용 : " + diaryReqDto);
		return diaryRepository.save(diaryReqDto.toEntity(principal.getUser()));
		
	}
	
	// 유저 리스트 
	@GetMapping("/list/{id}")
	public List<Map<String,Object>> list(@PathVariable Long id) {
		//
		//System.out.println("테스트 :" + param );
		System.out.println("테스트 :" + id);
		System.out.println("쿼리 결과 : " +diaryRepository.findByAdmin_no(id));
		return diaryRepository.findByAdmin_no(id);
	}
	
	
	@GetMapping("/detail/{id}")
	public Optional <Diary> datail(@PathVariable Long id) {
	
		return  diaryRepository.findByDiary_no(id);
	}
	
	@GetMapping("/list")
	public List<Diary> listAll() {
		// 컨트롤러 단에서 바로작성 가능
		List<Diary> diaryList = diaryRepository.findAll();
		
		return diaryList;
	}
	
	@GetMapping("/findByJpa/{id}")
	public Optional <Diary> findByJpa(@PathVariable Long id) {
		// 컨트롤러 단에서 바로작성 가능
		Optional <Diary> diary = diaryRepository.findById(id);
		
		return diary;
	}
	
	
}
