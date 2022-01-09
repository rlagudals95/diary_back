package com.cos.jwtex01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.jwtex01.domain.Diary;
import com.cos.jwtex01.domain.DiaryRepository;

@Service
public class DiaryService {
	
	@Autowired
	public DiaryRepository diaryRepository;
	
	public void update (Long id) {
		// ifPresent 활용한 update?
		Diary diary = diaryRepository.getOne(id);
		//diaryRepository.update(diary);
	}
	
}
