package com.mysite.sbb.answer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
	// Controller ==> Service
	
	// @RequestMapping : get, post 요청을 모두 처리
	
	private final AnswerService answerService;
	private final QuestionService questionService;
	
	// 답변글을 저장
	@PostMapping("/create/{id}")	// answer/create/{id}
	public String createAnswer(Model model, @PathVariable Integer id, 
			@RequestParam String content) {
		
		// id 변수가 잘 넘어오는지 출력	
//		System.out.println("===== id: " + id + "=====");
//		System.out.println("===== content: " + content + "=====");
		
		// 1. 변수 : id의 값으로 Question 객체를 받아와야 함.
		Question question = questionService.getQuestion(id);
		
		// 2. Service 에서 변수 2개를 넣어서 값을 Insert
		answerService.create(question, content);
		
		// question_detail 로 리턴 : get 방식으로 URL로 redirect
		return String.format("redirect:/question/detail/%s", id);
	}
	
}
