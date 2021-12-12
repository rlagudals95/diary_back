package com.cos.jwtex01.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.jwtex01.config.auth.LoginUser;
import com.cos.jwtex01.config.auth.Principal;

@RestController
public class TestController {
	
	@Autowired
	private NaverService naverService;
	
	// 유저 혹은 어드민이 접근 가능
	@PostMapping("/search/{url}")
	public String user(@PathVariable("url") String url) {
		//System.out.println("principal : "+principal.getUsername());
			
		return NaverService.main(url);
	}	
}











