package com.cos.jwtex01.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.jwtex01.domain.User;
import com.cos.jwtex01.domain.UserRepository;
import com.cos.jwtex01.dto.JoinReqDto;
import com.cos.jwtex01.service.AuthService;

import lombok.RequiredArgsConstructor;

import com.cos.jwtex01.config.Constants;
import com.cos.jwtex01.config.auth.LoginUser;
import com.cos.jwtex01.config.auth.Principal;

@RestController
@RequiredArgsConstructor
public class UserController {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private AuthService authService;
	
	
	@PostMapping("/join")
	public User join(@RequestBody JoinReqDto joinReqDto) {
		joinReqDto.setPassword(bCryptPasswordEncoder.encode(joinReqDto.getPassword()));
		return userRepository.save(joinReqDto.toEntity());
	}
	
	// 카카오 로그인창 호출
	@RequestMapping(value = "/login/getKakaoAuthUrl")
	public @ResponseBody String getKakaoAuthUrl(HttpServletRequest request) throws Exception {
		
		System.out.println("확인!! :" + Constants.kakaoAuthUrl + "//" + Constants.kakaoApiKey + "//" + Constants.redirectUri);
		String reqUrl = Constants.kakaoAuthUrl + "/oauth/authorize?client_id=" + Constants.kakaoApiKey + "&redirect_uri="+ Constants.redirectUri + "&response_type=code";
		System.out.println("완성 : "+ reqUrl);
		return reqUrl;
	}
	
	// 카카오 연동정보 조회
	@PostMapping(value = "/login/oauth_kakao")
	public Map<String, Object> oauthKakao(@RequestBody Map<String, Object> param ) throws Exception {
		String code= (String) param.get("code");
		System.out.println("#########" + code);
        String access_Token = authService.getAccessToken(code);
        System.out.println("###access_Token#### : " + access_Token);
        
        
        HashMap<String, Object> userInfo = authService.getUserInfo(access_Token);
        System.out.println("###access_Token#### : " + access_Token);
        System.out.println("###userInfo#### : " + userInfo.get("email"));
        System.out.println("###nickname#### : " + userInfo.get("nickname"));
        
        return userInfo; //본인 원하는 경로 설정
	}
	
	@PostMapping(value = "/setBirthday")
	public String setBirthday(@RequestBody Map<String, Object> param ) throws Exception {
		
		String username = (String) param.get("username");
		String birthday = (String) param.get("birthday");
		userRepository.setBirthday(username, birthday);
		
        return "생일 등록 성공"; //본인 원하는 경로 설정
	}
	
}





















