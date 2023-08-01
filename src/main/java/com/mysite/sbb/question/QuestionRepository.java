package com.mysite.sbb.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
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
	
	// JPA 메소드를 사용한 테이블 검색 : 기본적으로 2개의 메소드는 자동으로 등록되어있음
			// 그외는 등록해서 사용해야한다.
		// findAll()	: select * from question;	<== 모든 레코드 출력
		// findById(?)	: select * from question where id=?	<== id를 기준으로 레코드 1개
	
	// 검색된 레코드가 1개 일때는 Option에 저장
	// 검색된 레코드가 여러개 일때는 List에 저장
	// select * from question where subject='?'
	List<Question> findBySubject(String subject);
	
	// select * from question where content='?'
	List<Question> findByContent(String content);
	
	// 특정 컬럼의 값을 검색 : Like 검색 <== 레코드가 여러개가 검색 : List
	// select * from question where subject like '%?%'
	List<Question> findBySubjectLike(String subject);	

	// select * from question where content like '%?%'
	List<Question> findByContentLike(String content);
	
	// 제목 과 내용 컬럼에서 검색
	// select * from question where subject like '%?%' or content like '%?%'
	List<Question> findBySubjectLikeOrContentLike(String subject, String content);
	
	// 정렬해서 출력하는 메소드 생성 <== 간단하고 자주 사용하는것, 복잡한 쿼리 : JPQL, QueryDSL
	// 날짜를 기준으로 오름차순 정렬 (Asc)  : 1 >> 9, A >> Z, ㄱ >> ㅎ
	// select * from question order by create_date asc
	List<Question> findAllByOrderByCreateDateAsc();

	// 날짜를 기준으로 내림차순 정렬 (Desc) : 9 >> 1, Z >> A, ㅎ >> ㄱ
	// select * from question order by create_date desc
	List<Question> findAllByOrderByCreateDateDesc();
	
	// 제목으로 기준으로 검색후 날짜를 기준으로 오름차순 정렬
	// select * from question where subject Like '%?%' order by create_date asc
	List<Question> findBySubjectLikeOrderByCreateDateAsc(String subject);

	// select * from question where subject Like '%?%' order by create_date desc
	List<Question> findBySubjectLikeOrderByCreateDateDesc(String subject);
	
	// 검색 기능을 사용 (select, find)
	
	// save() : insert, update
	
	// delete() : delete
	
	// Question (질문)의 페이징 처리
	Page<Question> findAll(Pageable pageable);
	
	// 검색어를 처리한 페이징 처리 메소드
	@Query("select distinct q "
			+ "from Question q "
			+ "		LEFT OUTER JOIN SiteUser u1 "
			+ "			ON q.author = u1 "
			+ "		LEFT OUTER JOIN Answer a "
			+ "			ON a.question = q "
			+ "		LEFT OUTER JOIN SiteUser u2 "
			+ "			ON a.author = u2 "
			+ "where q.subject like %:kw% "
			+ "		or q.content like %:kw% "
			+ "		or u1.username like %:kw% "
			+ "		or a.content like %:kw% "
			+ "		or u2.username like %:kw%")
	Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
	
}
