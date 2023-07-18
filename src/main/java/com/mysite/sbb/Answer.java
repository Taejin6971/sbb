package com.mysite.sbb;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
	@Column(columnDefinition = "text")
	private String content;
	
	@CreatedDate
	private LocalDateTime createdate;	// 2023-07-18
		// JPA에서 필드이름을 : createDate <===> CREATE_DATE
	
	// Foreign Key
		// question <===> QUESTION_ID
	@ManyToOne		// 답변(Answer) : Many ===> 질문(Question) : One
	private Question question;
}
