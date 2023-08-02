package com.mysite.sbb.question;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

@SpringBootTest
class QuestionServiceTest3 {

	@Autowired
	QuestionService questionService;
	
	@Test
	void testGetList() {
		
		Page<Question> paging = questionService.getList(0, "");
		
//		System.out.println("검색된 갯수 : " + paging.getSize());	// 15 한페이지의 레코드 갯수
		System.out.println("검색된 갯수 : " + paging.getTotalElements());
	}

}
