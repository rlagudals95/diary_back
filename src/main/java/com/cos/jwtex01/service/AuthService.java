package com.cos.jwtex01.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.cos.jwtex01.config.Constants;
import com.cos.jwtex01.domain.User;
import com.cos.jwtex01.domain.UserRepository;
import com.cos.jwtex01.dto.JoinReqDto;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;
	
	@SuppressWarnings("unused")
	private void kakao () {
		// 카카오에 POST방식으로 key=value 데이터를 요청함. RestTemplate를 사용하면 요청을 편하게 할 수 있다.
		RestTemplate rt = new RestTemplate();

		// HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add("grant_type", "authorization_code");
			params.add("client_id", "{client_id}");
			params.add("redirect_uri", "http://localhost:8000/oauth2/kakao");
			params.add("code", "code");
			params.add("client_secret", "{secret_code}");

		// HttpHeader와 HttpBody를 HttpEntity에 담기 (why? rt.exchange에서 HttpEntity객체를 받게 되어있다.)
		HttpEntity<MultiValueMap<String, String>> kakaoRequest = new HttpEntity<>(params, headers);

		// HTTP 요청 - POST방식 - response 응답 받기
		ResponseEntity<String> response = rt.exchange(
		    "https://kauth.kakao.com/oauth/token",
		    HttpMethod.POST,
		    kakaoRequest,
		    String.class
		);
	}
	
	
	//유저정보조회
    public HashMap<String, Object> getUserInfo (String access_Token) {

        //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //    요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();
            String id = element.getAsJsonObject().get("id").getAsString();
            String birthday = kakao_account.getAsJsonObject().get("birthday").getAsString();
            
            userInfo.put("accessToken", access_Token);
            userInfo.put("nickname", nickname);
            userInfo.put("email", email);
            userInfo.put("id", id);
            userInfo.put("birthday", birthday);
            
            if ( userRepository.findByUsername(id) == null) {
            	JoinReqDto joinReqDto = new JoinReqDto();
            	
            	joinReqDto.setEmail(email);
            	joinReqDto.setUsername(id);
            	joinReqDto.setName(nickname);
            	joinReqDto.setBirthday(birthday);
            	joinReqDto.setJoin_type("02");
            	System.out.println("가입된 안된 id :" + id);
            	userRepository.save(joinReqDto.toEntity());
            } else {
            	System.out.println("가입된 id :" + id);
            	
            };
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return userInfo;
    }
    
  //토큰발급
  	public String getAccessToken (String authorize_code) {
          String access_Token = "";
          String refresh_Token = "";
          String reqURL = "https://kauth.kakao.com/oauth/token";

          try {
              URL url = new URL(reqURL);

              HttpURLConnection conn = (HttpURLConnection) url.openConnection();

              //  URL연결은 입출력에 사용 될 수 있고, POST 혹은 PUT 요청을 하려면 setDoOutput을 true로 설정해야함.
              conn.setRequestMethod("POST");
              conn.setDoOutput(true);

              //	POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
              BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
              StringBuilder sb = new StringBuilder();
              sb.append("grant_type=authorization_code");
              sb.append("&client_id="+ Constants.kakaoApiKey);  //본인이 발급받은 key
              sb.append("&redirect_uri=" + Constants.redirectUri);     // 본인이 설정해 놓은 경로
              sb.append("&code=" + authorize_code);
              bw.write(sb.toString());
              bw.flush();

              //    결과 코드가 200이라면 성공
              int responseCode = conn.getResponseCode();
              System.out.println("responseCode : " + responseCode);

              //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
              BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
              String line = "";
              String result = "";

              while ((line = br.readLine()) != null) {
                  result += line;
              }
              System.out.println("response body : " + result);

              //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
              JsonParser parser = new JsonParser();
              JsonElement element = parser.parse(result);

              access_Token = element.getAsJsonObject().get("access_token").getAsString();
              refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

              System.out.println("access_token : " + access_Token);
              System.out.println("refresh_token : " + refresh_Token);

              br.close();
              bw.close();
          } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }

          return access_Token;
      }
}
