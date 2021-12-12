package com.cos.jwtex01.web;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cos.jwtex01.naver.NaverCrawler;


/**
 * 네이버 크롤러 메인 클래스
 * 
 * @author steel
 * @since 2021.02.24
 */

@Service("naverService")
public class NaverService {
	
	public static String main(String url) {
              
	    String result = "";
        try {
	        NaverCrawler crawler = new NaverCrawler();
	        String _url = URLEncoder.encode(url, "UTF-8");
	        String response = crawler.search("Qjcu2eErcOe9ypZkNwbQ", "2S8N_LMwCP", _url);
	        
	        result = response;
	       
        } catch (Exception e) {
        	e.printStackTrace();
        }
        System.out.println(result);
        return result;
	}
}