package com.cos.jwtex01.controller;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.jwtex01.domain.User;
import com.cos.jwtex01.domain.UserRepository;
import com.cos.jwtex01.dto.JoinReqDto;
import com.cos.jwtex01.dto.LoginReqDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping("/join")
	public User join(@RequestBody JoinReqDto joinReqDto) {
		joinReqDto.setPassword(bCryptPasswordEncoder.encode(joinReqDto.getPassword()));
		return userRepository.save(joinReqDto.toEntity());
	}
	
	@GetMapping("/oauth2/kakao")
	public String kakao(@RequestParam Map<String, Object> param) {
		
		return "카카오 로그인 성공!";
	}
	
	
	
		
}











