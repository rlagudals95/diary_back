package com.cos.jwtex01.dto;

import com.cos.jwtex01.domain.Maxim;
import com.cos.jwtex01.domain.Post;
import com.cos.jwtex01.domain.User;

import lombok.Data;

@Data
public class MaximReqDto {
    private String content;
    
	public Maxim toEntity(User user) {
		return Maxim.builder()
				.content(content)
				.user(user)
				.build();
	}
}
