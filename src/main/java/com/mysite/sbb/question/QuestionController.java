package com.mysite.sbb.question;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/question")
@RequiredArgsConstructor	// 생성자를 이용한 객체 주입 방식
// class 내부의 final이 붙은 변수에 객체를 주입
@Controller
public class QuestionController {
	// Controller는 Repository를 직접 접근하지 않는다.
		// 중복된 코드, 보안
	// Controller는 Service를 접근한다.
	
//	private final QuestionRepository questionRepository;
	private final QuestionService questionService;
	private final UserService userService;

	// client의 /question/list 요청을 처리하는 메소드
	// http://localhost:9696/question/list
	// 리스트
	// http://localhost:9696/question/list?pqge=1
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
		// 1. client 요청을 받는다. http://localhost:9696/question/list
		
		// 2. 비즈니스 로직 처리
//		List<Question> questionList = this.questionRepository.findAll();
		Page<Question> paging = questionService.getList(page);
		
//		System.out.println("페이지 존재여부 : " + paging.isEmpty());
//		System.out.println("전체 레코드 수 : " + paging.getTotalElements());
//		System.out.println("전체 페이지 수 : " + paging.getTotalPages());
//		System.out.println("페이지당 출력할 레코드 갯수 : " + paging.getSize());
//		System.out.println("현재 페이지 : " + paging.getNumber());
//		System.out.println("이전 페이지 여부 : " + paging.hasPrevious());
//		System.out.println("다음 페이지 여부 : " + paging.hasNext());
		
		// 3. 받아온 List를 Client로 전송 (Model 객체에 저장해서 Client로 전송)
		model.addAttribute("paging", paging);
		
		return "question_list";
	}
	
	// 상세 내용
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable Integer id, Model model, AnswerForm answerForm) {

		// 1. 클라이언트 요청을 받는다. : http://localhost:9696/question/detail/{id}
		// 2. Service 에게 로직을 처리
		Question question = questionService.getQuestion(id);
		 
//		System.out.println("======Question=====");
//		System.out.println(question.getId());
//		System.out.println(question.getSubject());
//		System.out.println(question.getContent());
		
		// 3. model 객체에 백엔드의 값을 담아서 view페이지로 전송
		model.addAttribute("question", question);
		return "question_detail";
	}
	
	// 질문 등록 요청 (get 요청)
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		
		return "question_form";
	}
	
	// 폼에서 제목과 내용을 받아서 DB에 등록 로직
	@PreAuthorize("isAuthenticated()")
		// 인증된 사용자만 접근가능
		// 인증되지 않는 사용자가 접근시 : /user/login 페이지로 돌려줌 <== SpringSecurity
	@PostMapping("/create")			// /question/create
//	public String questionCreate(@RequestParam String subject, @RequestParam String content) {
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult,
			Principal principal) {
		
		System.out.println("현재 로그인한 계정 : " + principal.getName());
		
		// 제목과 내용을 받아서 DB에 저장
		System.out.println("제목(DTO): " + questionForm.getSubject());
		System.out.println("내용(DTO): " + questionForm.getContent());
		
		// 유효성 검사후 DB에 저장
		if (bindingResult.hasErrors()) {
			return "question_form";
		}
		
		// principal.getName() : 현재 로그인한 계정의 username 알아온다.
		// 로그인 하지않는 상태에서 : principal.getName() 을 호출하는 경우 오류페이지가 발생
		SiteUser siteUser = userService.getUser(principal.getName());
		
		// DB에 저장
		questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
		
		return "redirect:/question/list";
	}
	
	// 글 수정
	@PreAuthorize("isAuthenticated")
	@GetMapping("/modify/{id}")
	public String questionModify(@PathVariable("id") Integer id,
			QuestionForm questionForm , Principal principal) {
		
		// id 변수를
		Question question = questionService.getQuestion(id);
		
		//DB에서 가져온 question 객체의 값을 questionForm setter 주입
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		
		// question_form.html : 질문글 등록, 수정
		return "question_form";
	}
	
	// 글 수정
	@PreAuthorize("isAuthenticated")
	@PostMapping("/modify/{id}")
	public String questionModify(@PathVariable("id") Integer id,
			@Valid QuestionForm questionForm, BindingResult bindingResult,
			Principal principal) {
		
		// 글 수정시 제목과 내용을 반드시 체크후 수정하도록 
		if (bindingResult.hasErrors()) {
			return "question_form";
		}
		
		// 1. id 변수를 가지고 Question 객체 호출
		Question question = questionService.getQuestion(id);
		
		// 현재 로그온한 계정이 질문작성자가 아닐때 삭제할 권한이 없다고 오류메세지 발송후
		if (!question.getAuthor().getUsername().equals(principal.getName())) {
			// DB에 질문을 등록한 계정과 현재 호그온한 계정이 같지 않을때 예외 발생
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "글 수정 권한이 없습니다.");
		}
		
		// 글 수정
		questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
		
		// 글 수정후
		return String.format("redirect:/question/detail/%s", id);
	}
	
	// 글 삭제
	@PreAuthorize("isAuthenticated")
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		
		// 1. id 변수를 가지고 Question 객체 호출
		Question question = questionService.getQuestion(id);
		
		// 2.
		questionService.delete(question);
		
		// 삭제 후 "/"로 이동
		return "redirect:/";	// http://localhost:9696/
	}
	
	// 게시글 추천 등록
	@PreAuthorize("isAuthenticated")
	@GetMapping("/vote/{id}")
	public String questionVote(@PathVariable("id") Integer id,
			Principal principal) {
		
		// id 값을 가지고 question 객체 반환
		Question question = questionService.getQuestion(id);
		
		// principal 객체를 가지고 현재 로그인한 계정의 SiteUser 객체를 반환
		SiteUser siteUser = userService.getUser(principal.getName());
		
		// vote 메소드에 question 객체와 siteUser 객체를 매개변수로 던짐
		questionService.vote(question, siteUser);
		
		return String.format("redirect:/question/detail/%s", id);
	}
	
}
