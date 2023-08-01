package com.mysite.sbb.question;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

@SpringBootTest
class QuestionServiceTest2 {

	@Autowired
	QuestionService questionService;

	@Autowired
	UserService userService;
	
	@Test
	void testCreate() {
		
		SiteUser siteUser = userService.getUser("user2");
		
		for (int i = 1; i <= 1000; i++) {
			
			String subj = "제목" + i;
			String cont = "내용" + i;
			
			questionService.create(subj, cont, siteUser);
		}
		
	}

}
