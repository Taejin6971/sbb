package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Answer {
	// 답변글을 저장하는 테이블 : 자식 테이블
	
	// *주의 : Entity 클래스에는 @Getter/@Setter를 붙이지않는다 (DTO생성후 넣는다)
	
	// @Entity : Java의 클래스를 DB의 테이블로 맵핑
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
//	@Column(columnDefinition = "text")	// Oracle DB에 존재하지 않는 자료형
	@Column(length = 4000)
	private String content;
	
	@CreatedDate
	private LocalDateTime createDate;	// 2023-07-18
		// JPA에서 필드이름을 : createDate <===> CREATE_DATE
	
	private LocalDateTime modifyDate;
	
	// Foreign Key
		// question <===> QUESTION_ID
	@ManyToOne(fetch = FetchType.LAZY)	// 답변(Answer) : Many ===> 질문(Question) : One
	private Question question;
	
	// Foreign Key : SiteUserId
	@ManyToOne(fetch = FetchType.LAZY)
	private SiteUser author;
	
	// 답변과 추천인의 관계는 多 : 多
	// Set은 중복된 닶이 올수 없다
	// ANSWER_VOTER 테이블이 생성됨 : ANSWER_ID, VOTER_ID 자동생성됨
		// ANSWER_ID 컬럼은 Answer 테이블의 ID 컬럼을 참조
		// VOTER_ID 컬럼은 SiteUser 테이블의 ID 컬럼을 참조		
	@ManyToMany(fetch = FetchType.LAZY)		// 지연로딩 : 요청이 발생할때 값을 넣어서 작동
	Set<SiteUser> voter;
	
}
