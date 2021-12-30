package com.cos.jwtex01.dto;

import org.springframework.web.multipart.MultipartFile;

import com.cos.jwtex01.domain.Category;
import com.cos.jwtex01.domain.Diary;
import com.cos.jwtex01.domain.User;

import lombok.Data;

@Data
public class DiaryReqDto  {
    private String title;
    private String content;
    private String keyword;
    private Long category_no;
    private Long score;
    private MultipartFile file;
    
	public Diary toEntity(User user) {
		return Diary.builder()
				.title(title)
				.content(content)
				.category_no(category_no)
				.keyword(keyword)
				.user(user)
				.score(score)
				.build();
	}
}
