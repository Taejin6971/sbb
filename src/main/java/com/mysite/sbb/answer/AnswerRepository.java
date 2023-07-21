package com.mysite.sbb.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
	// AnswerRepository 는 JpaRepository의 모든 메소드를 상속
		// 메소드를 사용해서 Answer 테이블을 CRUD
	// findAll()	: select * from Answer;
	// save(answer) : insert into Answer values(?);
	
	// findAll()	: select * from Answer
	// findbyId(?)	: select * from Answer where id=?
	// save()		: insert, update
	// delete()		: delete
}
