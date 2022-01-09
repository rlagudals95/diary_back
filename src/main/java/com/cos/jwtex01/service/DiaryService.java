package com.cos.jwtex01.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.jwtex01.domain.Diary;
import com.cos.jwtex01.domain.DiaryRepository;

@Service
public class DiaryService {
	
	@Autowired
	public DiaryRepository diaryRepository;
	
	public Optional<Diary> update (Long id) {
		Optional<Diary> diary = diaryRepository.findById(id);
		
		//diaryRepository.update(diary);
		diary.ifPresent(selectDiary->{
			selectDiary.setContent("content");
			selectDiary.setTitle("title");
			selectDiary.setImage_url("title");
        });
		return diary;
	}
	
	public void delete (Long id) {
		Optional<Diary> diary = diaryRepository.findById(id);
		
		diary.ifPresent(selectDiary->{
			diaryRepository.delete(selectDiary);
        });
	}
	
}
