package com.mysite.sbb;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
	// QuestionRepository : Question 테이블을 CRUD 하는 메소드
	
	// Client ===> Controller ===> Service(비즈니스 로직처리)
	// ===> Repository(DAO, DB를 직접 접근) ===> Entity ===> DB
	
	// Repository : 메소드를 사용해서 DB의 테이블을 select, insert, update, delete
		// JpaRepository <Question, Integer> 인터페이스를 구연해서 생성해야한다.
			// Question : Entity class
			// Integer  : Question Entity 클래스의 Primary Key 컬럼의 DataType
	
	// JPA 메소드
		// findAll()	: select
		// save()		: insert, update
		// delete()		: delete
}