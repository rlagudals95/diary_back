package com.cos.jwtex01.config;

public class Constants {
	//aws
	public static final String S3_URL = "https://s3diary.s3.ap-northeast-2.amazonaws.com/";
	public static final String S3_NAME_DIARY = "s3diary";
	public static final String S3_TYPE_CATEGORY = "02";
	public static final String ACCESS_KEY = "AKIAYEBJVE42S46DX6XO";
	public static final String SECRET_KEY = "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";
	//naver
	public static final String naverClientId = "fkIjAvzP_QnqzpRVefmI";
	public static final String naverClientSecret = "H3gOnkdn_e";

	//kakao
	public static final String kakaoApiKey = "9a02524f15a3577f60c041db6dbef087";
	public static final String kakaoAuthUrl= "https://kauth.kakao.com";
	
	//auth
	//public static final String redirectUri = "http://localhost:3000/";
	//public static final String redirectUriProd = "ec2-3-34-146-254.ap-northeast-2.compute.amazonaws.com:8000/";
	
	//jwt
	public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 300*60*60;
    public static final String SIGNING_KEY = "rlagudals95";
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
}

