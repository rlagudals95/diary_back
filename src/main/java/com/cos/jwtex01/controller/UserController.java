package com.cos.jwtex01.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.jwtex01.domain.User;
import com.cos.jwtex01.domain.UserRepository;
import com.cos.jwtex01.dto.JoinReqDto;
import com.cos.jwtex01.dto.LoginReqDto;

import lombok.RequiredArgsConstructor;
import com.cos.jwtex01.config.Constants;

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
	
	// 카카오 로그인창 호출
	@RequestMapping(value = "/login/getKakaoAuthUrl")
	public @ResponseBody String getKakaoAuthUrl(HttpServletRequest request) throws Exception {
		
		System.out.println("확인!! :" + Constants.kakaoAuthUrl + "//" + Constants.kakaoApiKey + "//" + Constants.redirectUri);
		String reqUrl = Constants.kakaoAuthUrl + "/oauth/authorize?client_id=" + Constants.kakaoApiKey + "&redirect_uri="+ Constants.redirectUri + "&response_type=code";
		System.out.println("완성 : "+ reqUrl);
		return reqUrl;
	}
	
	// 카카오 연동정보 조회
//	@RequestMapping(value = "/login/oauth_kakao")
//	public String oauthKakao(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		
//	    String code = request.getParameter("code");
//	    String error = request.getParameter("error");
//	    // 카카오로그인 페이지에서 취소버튼 눌렀을경우
//	    if (error != null) {
//	        if (error.equals("access_denied")) {
//	            return "redirect:/login";
//	        }
//	    }
//
//	    String accessToken = getAccessToken(code);
//	    String kakaoUniqueNo = getKakaoUniqueNo(accessToken);
//
//	    if (kakaoUniqueNo != null && !kakaoUniqueNo.equals("")) {
//	        /** 
//	        
//	            TO DO : 리턴받은 kakaoUniqueNo에 해당하는 회원정보 조회 후 로그인 처리 후 메인으로 이동
//	        
//	        */
//
//	    return "redirect:/";
//	    
//	    // 카카오톡 정보조회 실패했을경우
//	    } else {
//	        throw new ErrorMessage("카카오톡 정보조회에 실패했습니다.");
//	    }
//
//	}
}





















