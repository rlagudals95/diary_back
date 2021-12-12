package com.cos.jwtex01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class Beans {
	  @Bean
	   public CorsFilter corsFilter() {
	      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	      CorsConfiguration config = new CorsConfiguration();
	      config.setAllowCredentials(true);
	      config.addAllowedOrigin("*"); // e.g. http://domain1.com
	      config.addAllowedHeader("*");
	      config.addAllowedMethod("*");
	      config.addExposedHeader("Authorization"); // response 값에 token실어 보내기
	      
	      source.registerCorsConfiguration("/**", config);//cors....
	      return new CorsFilter(source);
	   }
	   
	    @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
}
