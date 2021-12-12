package com.cos.jwtex01.dto;


import com.cos.jwtex01.domain.User;

import lombok.Data;

@Data
public class JoinReqDto {
	private String username;
	private String password;
	private String email;
	private String name;
	
	public User toEntity() {
		return User.builder()
				.username(username)
				.password(password)
				.email(email)
				.name(name)
				.role("ROLE_USER")
				.build();
	}
}
