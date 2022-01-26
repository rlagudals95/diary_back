package com.cos.jwtex01.controller;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.jwtex01.domain.MaximRepository;
import com.cos.jwtex01.domain.User;
import com.cos.jwtex01.domain.UserRepository;
import com.cos.jwtex01.dto.JoinReqDto;
import com.cos.jwtex01.dto.MaximReqDto;
import com.cos.jwtex01.service.AuthService;
import com.cos.jwtex01.service.NaverAuthService;
import com.github.scribejava.core.model.OAuth2AccessToken;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;

import com.cos.jwtex01.config.Constants;
import com.cos.jwtex01.config.auth.LoginUser;
import com.cos.jwtex01.config.auth.Principal;


@RestController
@RequiredArgsConstructor
@RequestMapping("/maxim")
public class MaximController {
	
	@Autowired
	private MaximRepository maximRepository;
	
	@PostMapping("/post")
	public void post (@RequestBody MaximReqDto maximReqDto, @LoginUser Principal principal ) {
		
		maximRepository.save(maximReqDto.toEntity(principal.getUser()));
	}
}





















