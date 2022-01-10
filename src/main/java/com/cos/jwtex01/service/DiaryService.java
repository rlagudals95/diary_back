package com.cos.jwtex01.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.jwtex01.domain.Diary;
import com.cos.jwtex01.domain.DiaryRepository;
import com.cos.jwtex01.dto.DiaryReqDto;

@Service
public class DiaryService {
	
	@Autowired
	public DiaryRepository diaryRepository;
	
	public Optional<Diary> update (DiaryReqDto diaryReqdto , long id) {
		
		//long id = diaryReqdto.getDiary_no();
		
		Optional<Diary> diary = diaryRepository.findById(id);
		
		//diaryRepository.update(diary);
		diary.ifPresent(selectDiary->{
			selectDiary.setContent(diaryReqdto.getTitle());
			selectDiary.setTitle(diaryReqdto.getContent());
			selectDiary.setScore(diaryReqdto.getScore());
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
