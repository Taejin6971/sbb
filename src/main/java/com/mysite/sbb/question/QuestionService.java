package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;	// 주의해서 import
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

	private final QuestionRepository questionRepository;

	// Question 테이블의 모든 레코드를 읽어와서 List<Question> 으로 값을 리턴하는 메소드
	// 페이징 처리되지 않는 모든 레토드를 리턴 (사용중지)
//	public List<Question> getList() {
//		List<Question> questionList = questionRepository.findAll();
//		return questionList;
////	return questionRepository.findAll();
//	}

	// 페이징 처리해서 리턴으로 돌려줌 (사용)
	public Page<Question> getList(int page) {
		
		// Pageable 객체의 특정 컬럼을 정렬할 객체를 생성해서 인자로 넣어준다
		// Sort Import시 주의 : import org.springframework.data.domain.Sort;
		List<Sort.Order> sorts = new ArrayList();
		sorts.add(Sort.Order.desc("id"));
		
		// page 변수 : 클라이언트에서 파라메터로 요청한 페이지 번호
		// 10 : 한 페이지에서 출력할 레코드 갯수
		// id 컬럼을 desc 
		Pageable pageable = PageRequest.of(page, 15, Sort.by(sorts));
		
		Page<Question> pageQuestion = questionRepository.findAll(pageable);
		
		return pageQuestion;
	}
	
	// 글 상세 페이지
	public Question getQuestion(Integer id) {
		// findById(?)
		// select * from question where id=?

		Optional<Question> question = questionRepository.findById(id);
		
		if (question.isPresent()) {
			// optional 내부의 객체가 != null
			// Question 객체를 리턴으로 돌려줌
			return question.get();
			
		} else {
			// optional 내부의 객체가 = null
			// 예외를 강제로 발생시킴 : DataNotFoundException
			// throw  : 예외를 강제로 발생시킴
			// throws : 메소드에서 메소드를 호출하는 곳에서 예외를 처리하도록 예외를 전가
			throw new DataNotFoundException("question not found");
		}
	}
	
	// 질문 제목 + 질문내용 DB에 저장 : insert, update, delete <== void, 리턴X
	public void create(String subject, String content, SiteUser siteUser) {
		Question question = new Question();
		
		question.setSubject(subject);
		question.setContent(content);
		question.setCreateDate(LocalDateTime.now());
		
		// 추가
		question.setAuthor(siteUser);
		
		questionRepository.save(question);
	}

}