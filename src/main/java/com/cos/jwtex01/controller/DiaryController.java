package com.cos.jwtex01.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.jwtex01.config.auth.LoginUser;
import com.cos.jwtex01.config.auth.Principal;
import com.cos.jwtex01.domain.Category;
import com.cos.jwtex01.domain.CategoryRepository;
import com.cos.jwtex01.domain.Diary;
import com.cos.jwtex01.domain.DiaryRepository;
import com.cos.jwtex01.dto.DiaryReqDto;
import com.cos.jwtex01.service.GrammarService;
import com.cos.jwtex01.service.SpellService;
import com.cos.jwtex01.service.WorkcheckService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
@RequestMapping("/diary")
public class DiaryController {
	
	@Autowired
	private GrammarService grammarService;
	
	@Autowired
	private WorkcheckService workcheckService;
	
	@Autowired
	private SpellService spellService;
	
	private final DiaryRepository diaryRepository;
	
	private final CategoryRepository categoryRepository;
	
	@PostMapping("/main")
	public String main(@LoginUser Principal principal) {
		
		return (String) diaryRepository.findKeyword(principal.getUser().getAdmin_no());
	}
	
	
	@PostMapping("/post")
	public Diary post(@RequestBody DiaryReqDto diaryReqDto , @LoginUser Principal principal ) {
		System.out.println("다이어리 추가 : "+diaryReqDto);
		// progress에 score 더하는 로직추가
		// 게시물의 카테고리 score 가져오자	
		Long category_no = diaryReqDto.getCategory_no();	
		//Optional<Category> category = categoryRepository.findById(category_no);
		int before_progress = categoryRepository.findByCategory_no(category_no);
		
		int after_progress = (int) (before_progress + diaryReqDto.getScore());
		if(after_progress >= 100) {
			categoryRepository.updateCategoryComplete(category_no);
		} else {
			categoryRepository.updateCategoryProgress(after_progress, category_no );
		}
			
		return diaryRepository.save(diaryReqDto.toEntity(principal.getUser()));	
	}
	
	// 유저 리스트 
	@PostMapping("/list")
	public List<Map<String, Object>> list(@LoginUser Principal principal, @RequestBody Map<String, Object> param) {
		//Pageable paging = PageRequest.of(0, 10, Sort.Direction.DESC, "create_date");
		
		System.out.println("페이징 : "+ param);
		System.out.println("어드민 넘qj : "+principal.getUser().getAdmin_no());
		return diaryRepository.findByAdmin_no(principal.getUser().getAdmin_no(), param.get("size"), param.get("page"));
	}
	
	
	@GetMapping("/detail/{id}")
	public Optional <Diary> datail(@PathVariable Long id ) {
		return  diaryRepository.findByDiary_no(id);
	}
	
	@GetMapping("/list/all")
	public List<Diary> listAll() {
		List<Diary> diaryList = diaryRepository.findAll();
		return diaryList;
	}
	
	@GetMapping("/findByJpa/{id}")
	public Optional <Diary> findByJpa(@PathVariable Long id) {
		Optional <Diary> diary = diaryRepository.findById(id);
		
		return diary;
	}
	
	@PostMapping("/detail/{id}")
	public Optional<Diary> deatil(@PathVariable Long id) {
		Optional<Diary> diary = diaryRepository.findById(id);
		return diary;
	}
	
	@PostMapping("/grammar")
	public String grammar(@RequestBody Map<String, Object> param) {
		return grammarService.grammarCorrect((String)param.get("content"));
	}
	
	@GetMapping("/check")
	public String check(@RequestBody Map<String, Object> param) {
		String content = (String) param.get("content");
		return workcheckService.parsing(content);
	}
	
	@GetMapping("/spell")
	public void spell(@RequestBody Map<String, Object> param) {
		String text = (String) param.get("content");
		System.out.println("이거 검사해 :" +text);
		try {
            spellService.check(text);
        }
        catch (Exception e) {
            System.out.println (e);
        }
	}
}
