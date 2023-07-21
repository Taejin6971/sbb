package com.mysite.sbb.question;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

	private final QuestionRepository questionRepository;

	// Question 테이블의 모든 레코드를 읽어와서 List<Question> 으로 값을 리턴하는 메소드
	public List<Question> getList() {

//		List<Question> questionList = questionRepository.findAll();
//		return questionList;
		return questionRepository.findAll();
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
}
