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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.jwtex01.domain.User;
import com.cos.jwtex01.domain.UserRepository;
import com.cos.jwtex01.dto.JoinReqDto;
import com.cos.jwtex01.service.AuthService;
import com.cos.jwtex01.service.NaverAuthService;
import com.github.scribejava.core.model.OAuth2AccessToken;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;

import com.cos.jwtex01.config.Constants;


@Component
@RestController
@RequiredArgsConstructor
public class UserController {
	
	@Value("${redirectUri}")
	private String redirectUri;
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private NaverAuthService naverAuthService;
	
	
	@PostMapping("/join")
	public User join(@RequestBody JoinReqDto joinReqDto) {
		joinReqDto.setPassword(bCryptPasswordEncoder.encode(joinReqDto.getPassword()));
		return userRepository.save(joinReqDto.toEntity());
	}
	
	// 카카오 로그인창 호출
	@RequestMapping(value = "/login/getKakaoAuthUrl")
	public @ResponseBody String getKakaoAuthUrl(HttpServletRequest request) throws Exception {
		System.out.println("환경변수확인 : " + redirectUri);
		System.out.println("확인!! :" + Constants.kakaoAuthUrl + "//" + Constants.kakaoApiKey + "//" + Constants.redirectUri);
		String reqUrl = Constants.kakaoAuthUrl + "/oauth/authorize?client_id=" + Constants.kakaoApiKey + "&redirect_uri="+ redirectUri + "&response_type=code";
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
	
	@PostMapping(value = "/getBirthday")
	public User getBirthday(@RequestBody Map<String, Object> param ) throws Exception {
		
		String username = (String) param.get("username");
		
		return userRepository.findByUsername(username);
	}
	
	// 네이버 로그인창 호출
	@RequestMapping(value = "/login/getNaverAuthUrl")
	public @ResponseBody String getNaverAuthUrl(HttpSession session) throws Exception {
	    String reqUrl = naverAuthService.getAuthorizationUrl(session);
	    return reqUrl;
	}
	
	@RequestMapping(value = "/login/oauth_naver")
	public void oauthNaver(HttpServletRequest request, HttpServletResponse response) throws Exception {

	    JSONParser parser = new JSONParser();
	    Gson gson = new Gson();

	    HttpSession session = request.getSession();
	    String code = request.getParameter("code");
	    String state = request.getParameter("state");
	    String error = request.getParameter("error");

	    // 로그인 팝업창에서 취소버튼 눌렀을경우
	    if ( error != null ){
	        if(error.equals("access_denied")){
	            //return "redirect:/login";
	        }
	    }

	    OAuth2AccessToken oauthToken;
	    oauthToken = naverAuthService.getAccessToken(session, code, state);
	    //로그인 사용자 정보를 읽어온다.
	    String loginInfo = naverAuthService.getUserProfile(session, oauthToken);
	    System.out.println("네이버 auth : " +loginInfo);
	    // JSON 형태로 변환
	  
	    
	    Object obj = parser.parse(loginInfo);
	    JSONObject jsonObj = (JSONObject) obj;
	    JSONObject callbackResponse = (JSONObject) jsonObj.get("response");
	    
	    String naverUniqueNo = callbackResponse.get("id").toString();
	    
	    if (naverUniqueNo != null && !naverUniqueNo.equals("")) {

	        /** 
	            TO DO : 리턴받은 naverUniqueNo 해당하는 회원정보 조회 후 로그인 처리 후 메인으로 이동
	        */

	    // 네이버 정보조회 실패
	    } else {
	        throw new Exception("네이버 정보조회에 실패했습니다.");
	    }

	}
}





















