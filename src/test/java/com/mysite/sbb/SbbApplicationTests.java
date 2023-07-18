package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SbbApplicationTests {

	// QuestionRepository 객체 주입
	@Autowired
	private QuestionRepository qr;

//	@Test
	void contextLoads() {
		Question q1 = new Question();
		q1.setSubject("오늘의 날씨는?");
		q1.setContent("오늘의 날씨는 어떤지 궁굼합니다.");
		q1.setCreateDate(LocalDateTime.now());
		qr.save(q1);

		Question q2 = new Question();
		q2.setSubject("스프링 부트 질문");
		q2.setContent("스프링 부트가 무엇인지 궁굼합니다.");
		q2.setCreateDate(LocalDateTime.now());
		qr.save(q2);

		Question q3 = new Question();
		q3.setSubject("오늘의 스포츠는?");
		q3.setContent("오늘의 스포츠는 어떤지 궁굼합니다.");
		q3.setCreateDate(LocalDateTime.now());
		qr.save(q3);

		Question q4 = new Question();
		q4.setSubject("미국의 날씨는");
		q4.setContent("미국의 날씨가 어떤지 궁굼합니다.");
		q4.setCreateDate(LocalDateTime.now());
		qr.save(q4);
	}

//	@Test
	void recodeCount() {
		List<Question> all = qr.findAll();
			// qr : Question 테이블과 연결된 객체
			// findAll() : select * from question;

		// assertEquals (기대치, 값);
		assertEquals(4, all.size());
	}
	
//	@Test
	void subjectTest() {
		List<Question> all = qr.findAll();
		
		Question q = all.get(1);
		
		assertEquals("스프링 부트 질문", q.getSubject());
	}
}