package com.cos.jwtex01.dto;


import com.cos.jwtex01.domain.User;

import lombok.Data;

@Data
public class JoinReqDto {
	private String username;
	private String password;
	private String email;
	private String name;
	private String birthday;
	private String join_type;
	
	public User toEntity() {
		return User.builder()
				.username(username)
				.password(password)
				.email(email)
				.name(name)
				.role("ROLE_USER")
				.join_type("01")
				.birthday(birthday)
				.build();
	}
}
