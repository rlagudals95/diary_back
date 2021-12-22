package com.cos.jwtex01.dto;

import com.cos.jwtex01.domain.Diary;
import com.cos.jwtex01.domain.User;

import lombok.Data;

@Data
public class DiaryReqDto {
    private String title;
    private String content;
    private String category;
    private String keyword;
    
	public Diary toEntity(User user) {
		return Diary.builder()
				.title(title)
				.content(content)
				.category(category)
				.keyword(keyword)
				.user(user)
				.build();
	}
}
