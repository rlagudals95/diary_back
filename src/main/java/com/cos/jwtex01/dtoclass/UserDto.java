package com.cos.jwtex01.dtoclass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class UserDto {
	
	@Data
	@AllArgsConstructor
	@Builder
	public static class Info {
		private long id;
		private String username;
		private String email;
		private String image_url;
	};
	
	@Data
	public static class Request {
		private long id;
	};
	
	@Data
	@AllArgsConstructor
	public static class Response {
		private Info info;
		private int resultCode;
		private String returnMessage;
	};
}
