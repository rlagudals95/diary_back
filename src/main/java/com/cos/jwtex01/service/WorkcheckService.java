//package com.cos.jwtex01.service;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.net.URL;
//import java.net.URLEncoder;
//
//public class WorkcheckService {
//	public String parsing(String txt) {
//		String result = null;
//		try {
//			String param = encodeURIComponent(txt); //GET 방식으로 전달된 값(encodeURIComponent함수 안에서 인코딩 진행)
//			String line;
//			URL url = new URL(
//					"https://m.search.naver.com/p/csearch/dcontent/spellchecker.nhn?_callback=window.__jindo2_callback._spellingCheck_0&q="+param);
//			BufferedReader bin = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
//			//네이버 맞춤법검사기에 request하여 얻은 response값 line에 저장
//			while ((line = bin.readLine()) != null) result += line;
//			bin.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		//필요한 부분만 파싱
//		result = result.substring(result.indexOf("\"html\":")+8, result.length()-6);
//		return result;
//	}
//	
//	//url parameter 객체 인코딩을 위한 함수
//	public static String encodeURIComponent(String s) {
//		String result = null;
//		try {
//			result = URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20").replaceAll("\\%21", "!")
//					.replaceAll("\\%27", "'").replaceAll("\\%28", "(").replaceAll("\\%29", ")")
//					.replaceAll("\\%7E", "~");
//		}
//		catch (UnsupportedEncodingException e) {
//			result = s;
//		}
//		return result;
//	}
//}
