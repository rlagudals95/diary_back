package com.cos.jwtex01.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

@Service
public class GrammarService {
	static String siteUrl = "https://search.naver.com/search.naver?where=nexearch&query=%EB%84%A4%EC%9D%B4%EB%B2%84+%EB%A7%9E%EC%B6%A4%EB%B2%95+%EA%B2%80%EC%82%AC%EA%B8%B0&ie=utf8&sm=tab_she&qdt=0";//맞춤법검사 주소
	static WebDriver driver;

	public String grammarCorrect(String source) { //배포용이 아니라 그냥 메인 함수 안에 코드를 작성.
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(siteUrl);
		
		String result = correctIt(source);
		System.out.println(result);
		return result;
	}

	private static void wait(int second) { //웹 자동화이기 때문에 버튼을 누르고 나면 조금 기다려주기 위해 만든 메소드. 10을 넣으면 1초를 기다린다.
		try {
			Thread.sleep(second * 100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String correctIt(String source) { //네이버에서 맞춤법 교정하는 메소드
		
		System.out.println("드라이버 확인 : "+ driver);
		
		driver.findElement(By.cssSelector(
				"#grammar_checker > div.check_box > div.text_box._original > div > div.text_area > textarea")).clear();//글쓰기 영역을 깨끗이 지운다.
		driver.findElement(By.cssSelector(
				"#grammar_checker > div.check_box > div.text_box._original > div > div.text_area > textarea"))
				.sendKeys(source); //엑셀에 작성한 내용(source)를 글쓰기 영역에 붙여넣는다.
		driver.findElement(By.cssSelector(
				"#grammar_checker > div.check_box > div.text_box._original > div > div.check_info > button")).click(); //검사하기 버튼을 누른다.
		wait(8); //0.8초 대기

		return driver
				.findElement(By.cssSelector(
						"#grammar_checker > div.check_box > div.text_box.right._result.result > div > div.text_area"))
				.getText().replace("맞춤법검사기 결과 영역", ""); //결과값을 출력하되, div영역 안에 들어있는 문구를 없앤 후 출력한다.
	}

	
}