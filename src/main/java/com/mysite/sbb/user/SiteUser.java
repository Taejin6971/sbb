package com.mysite.sbb.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;	//Spring 3.1.1: jakarta, //Spring 2.x.x: javax
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
 
@Data
@Entity
public class SiteUser {
	// 사용자 정보를 입력하는 테이블과 매핑된 클래스 : @Entity
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;			// int: 65536개의 레코드 <== Primary Key
	
	@Column(unique = true)
	private String username;	// 계정 : ID <== Unique
	
	private String password;	// 패스워드 <== Spring Security에서는 암호화 저장되어야함

	@Column(unique = true)
	private String email;		// 메일주소 <== Unique
	
}
