package com.mysite.sbb.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor	// 생성자를 이용한 객체 주입 방식
// class 내부의 final이 붙은 변수에 객체를 주입
@Controller
public class QuestionController {
	// Controller는 Repository를 직접 접근하지 않는다.
		// 중복된 코드, 보안
	// Controller는 Service를 접근한다.
	
//	private final QuestionRepository questionRepository;
	private final QuestionService questionService;

	// client의 /question/list 요청을 처리하는 메소드
	// http://localhost:9696/question/list
	// 리스트
	@GetMapping("/question/list")
	public String list(Model model) {
		// 1. client 요청을 받는다. http://localhost:9696/question/list
		
		// 2. 비즈니스 로직 처리
//		List<Question> questionList = this.questionRepository.findAll();
		List<Question> questionList = questionService.getList();
		
		// 3. 받아온 List를 Client로 전송 (Model 객체에 저장해서 Client로 전송)
		model.addAttribute("questionList", questionList);
		
		return "question_list";
	}
	
	// 상세 내용
	@GetMapping("/question/detail/{id}")
	public String detail(@PathVariable Integer id, Model model) {

		// 1. 클라이언트 요청을 받는다. : http://localhost:9696/question/detail/{id}
		// 2. Service 에게 로직을 처리
		Question question = questionService.getQuestion(id);
		 
		// 3. model 객체에 백엔드의 값을 담아서 view페이지로 전송
		model.addAttribute("question", question);
		
		return "question_detail";
	}
	
	// 질문 등록 요청 (get 요청)
	@GetMapping("/question/create")
	public String questionCreate(QuestionForm questionForm) {
		
		return "question_form";
	}
	
	// 폼에서 제목과 내용을 받아서 DB에 등록 로직
	@PostMapping("/question/create")
//	public String questionCreate(@RequestParam String subject, @RequestParam String content) {
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
		// 제목과 내용을 받아서 DB에 저장
		System.out.println("제목(DTO): " + questionForm.getSubject());
		System.out.println("내용(DTO): " + questionForm.getContent());
		
		// 유효성 검사후 DB에 저장
		if (bindingResult.hasErrors()) {
			return "question_form";
		}
		
		// DB에 저장
		questionService.create(questionForm.getSubject(), questionForm.getContent());
		
		return "redirect:/question/list";
	}
	
}
