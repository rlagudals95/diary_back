package com.cos.jwtex01.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cos.jwtex01.config.auth.LoginUser;
import com.cos.jwtex01.config.auth.Principal;
import com.cos.jwtex01.domain.Diary;
import com.cos.jwtex01.dto.DiaryReqDto;

@Controller
@RequestMapping("canvas")
public class CanvasController {
	
	@PostMapping("/post")
	@ResponseBody
	@Transactional(isolation = Isolation.DEFAULT)
	public Map<String, Object> post(
			@RequestParam(value = "file", required = false) MultipartFile file// upload file
	) throws IOException {
		
		Map<String , Object> result = new HashMap<String,Object>();
	
		
		return result;
	}

}
