package com.cos.jwtex01.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.amazonaws.services.s3.internal.Constants;
import com.cos.jwtex01.config.auth.LoginUser;
import com.cos.jwtex01.config.auth.Principal;
import com.cos.jwtex01.domain.Category;
import com.cos.jwtex01.domain.CategoryRepository;
import com.cos.jwtex01.domain.Diary;
import com.cos.jwtex01.domain.DiaryRepository;
import com.cos.jwtex01.dto.CategoryReqDto;
import com.cos.jwtex01.dto.DiaryReqDto;
import com.cos.jwtex01.service.AWSservice;
import com.cos.jwtex01.service.CategoryService;
import com.cos.jwtex01.service.DiaryService;
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
	
	@Autowired
	private DiaryService diaryService;
	
	@Autowired
	private AWSservice awsService;
	
	@Autowired
	private CategoryService categoryService;
	
	private final DiaryRepository diaryRepository;
	
	private final CategoryRepository categoryRepository;
	
	@PostMapping("/main")
	public String main(@LoginUser Principal principal) {
		
		return (String) diaryRepository.findKeyword(principal.getUser().getAdmin_no());
	}
	
	
	@PostMapping("/post")
	@ResponseBody
	@Transactional(isolation=Isolation.DEFAULT)
	public Diary post(
			@RequestParam Map<String, Object> param,
			@LoginUser Principal principal,
			@RequestParam(value="file",required=false) MultipartFile[] files // upload file
			) throws IOException {
		
		DiaryReqDto diaryReqDto = new DiaryReqDto ();
		Long category_no = Long.parseLong((String) param.get("category_no"));	

		// category progress
		int before_progress = categoryRepository.findByCategory_no(category_no);
		int score = Integer.parseInt((String) param.get("score")) ;
		
		int after_progress = (int) (before_progress + score);
		if(after_progress >= 100) {
			categoryRepository.updateCategoryComplete(category_no);
		} else {
			categoryRepository.updateCategoryProgress(after_progress, category_no );
		}
		
		awsService.uploadFile(files);
		//awsService.uploadFile(files);
		// dto set
		diaryReqDto.setCategory_no(category_no);
		diaryReqDto.setContent((String) param.get("content"));
		diaryReqDto.setScore(Integer.parseInt((String) param.get("score")));
		diaryReqDto.setTitle((String) param.get("title"));	
		diaryReqDto.setImage_url(awsService.uploadFile(files));	
		
		return diaryRepository.save(diaryReqDto.toEntity(principal.getUser()));	
	}
	
	@PostMapping("/post2")
	@ResponseBody
	@Transactional(isolation=Isolation.DEFAULT)
	public Diary post2(
			@RequestPart(value ="key") DiaryReqDto diaryReqDto,
			@RequestParam(value="file",required=false) MultipartFile[] files, // upload file
			@LoginUser Principal principal	
			) throws IOException {
	
		Long category_no = diaryReqDto.getCategory_no();	
		
		// category progress
		int before_progress = categoryRepository.findByCategory_no(category_no);
		int score = diaryReqDto.getScore();
		int after_progress = (int) (before_progress + score);
		
		categoryService.progress(after_progress, category_no);
		
		awsService.uploadFile(files);
		diaryReqDto.setImage_url(awsService.uploadFile(files));	
		
		return diaryRepository.save(diaryReqDto.toEntity(principal.getUser()));	
	}
	
	// 유저 리스트 
	@PostMapping("/list")
	public List<Map<String, Object>> list(@LoginUser Principal principal, @RequestBody Map<String, Object> param) {
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
	
	@PostMapping("/delete/{id}")
	public void delete(@PathVariable Long id, @RequestBody Map<String, Object> param) {
		System.out.println("카테고리 삭제 파람 : " +param);
		int _category_no = (int) param.get("category_no") ;
		long category_no = _category_no;
		
		int before_progress = categoryRepository.findByCategory_no(category_no);
		int score = (int) param.get("score");
		int after_progress = (int) (before_progress - score);
		
		categoryRepository.updateCategoryProgress(category_no, after_progress);
		
		diaryService.delete(id);
	}
	
	@PostMapping("/edit/{id}")
	public void update(
			@PathVariable Long id,
			@LoginUser Principal principal,
			@RequestPart(value = "key") DiaryReqDto diaryReqDto,
			@RequestPart(value = "file") MultipartFile[] file) 
	{
		diaryService.update(diaryReqDto, id);
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
