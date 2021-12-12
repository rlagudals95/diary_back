package com.cos.jwtex01.dto;

import com.cos.jwtex01.domain.Diary;
import com.cos.jwtex01.domain.User;

import lombok.Data;

@Data
public class DiaryReqDto {
    private String title;
    private String content;
    
	public Diary toEntity(User user) {
		return Diary.builder()
				.title(title)
				.content(content)
				.user(user)
				.build();
	}
}
