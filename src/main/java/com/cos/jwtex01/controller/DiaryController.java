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
import com.cos.jwtex01.dto.DiaryReqDto;
import com.cos.jwtex01.service.AWSservice;
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
	private AWSservice awsService;
	
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
		//System.out.println("다이어리 추가 : "+param);
		//System.out.println("파일 추가 : "+files);
		
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
		//System.out.println("이미지 추가 : " + awsService.uploadFile(files));
		//awsService.uploadFile(files);
		// dto set
		diaryReqDto.setCategory_no(category_no);
		diaryReqDto.setContent((String) param.get("content"));
		diaryReqDto.setScore(Long.parseLong((String) param.get("score")));
		diaryReqDto.setTitle((String) param.get("title"));	
		diaryReqDto.setImage_url(awsService.uploadFile(files));	
		return diaryRepository.save(diaryReqDto.toEntity(principal.getUser()));	
	}

	@PostMapping(path="/post/test")
	@ResponseBody
	public String save(
			HttpServletRequest request,
			@RequestParam Map<String, Object> param,
			@RequestParam(value="file",required=false) MultipartFile[] files // upload file
			) throws Exception {
		System.out.println("확인해보자 :" + request );
		System.out.println("파일확인해보자 :" + files[0] );
		System.out.println("요청확인해보자 :" + param );
		param.put("files", files);
		MultipartFile[] files2 = (MultipartFile[]) param.get("files");
		
		param.put("file", files2);
		for(MultipartFile img : files) {
			if(img != null && !img.isEmpty()) {
				System.out.println("img : "+ img);
			}
			System.out.println("이미지 파일 "+img);
		}
		return "save";
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
