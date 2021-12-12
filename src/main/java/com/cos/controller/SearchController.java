package com.cos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.jwtex01.config.auth.LoginUser;
import com.cos.jwtex01.config.auth.Principal;

//@RestController 
//public class SearchController {
//	
//	// 유저 혹은 어드민이 접근 가능
//	@GetMapping("/search")
//	public String search() {
//		//System.out.println("principal : "+principal.getUsername());
//		return "연결완료";
//	}	
//}
