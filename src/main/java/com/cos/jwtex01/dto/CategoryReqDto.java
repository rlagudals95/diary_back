package com.cos.jwtex01.dto;

import com.cos.jwtex01.domain.Category;
import com.cos.jwtex01.domain.Diary;
import com.cos.jwtex01.domain.User;

import lombok.Data;

@Data
public class CategoryReqDto {
    private String name;
    
	public Category toEntity(User user) {
		return Category.builder()
				.name(name)
				.user(user)
				.build();
	}
}
