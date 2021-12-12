package com.cos.jwtex01.controller;

import java.util.List;
import java.util.Map;

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
		
		return diaryRepository.save(diaryReqDto.toEntity(principal.getUser()));
		
	}
	
	@GetMapping("/list/{id}")
	public List<Map<String,Object>> list(@PathVariable Integer id) {
		//
		//System.out.println("테스트 :" + param );
		System.out.println("테스트 :" + id);
		System.out.println("쿼리 결과 : " +diaryRepository.findByAdmin_no(id));
		return diaryRepository.findByAdmin_no(id);
	}
	
	
}
