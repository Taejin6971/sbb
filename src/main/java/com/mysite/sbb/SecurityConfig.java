package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableMethodSecurity		
// SpringSecurity에서 Controller에 할당된 @PreAuthorize("isAuthenticated()") 를 작동시켜주는 어노테이션
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	// Spring Security 를 제어하는 설정 클래스
	/*
	 	SbbSbbApplication.java : 스프링 부트가 부팅될때 모든 설정을 적용되는 파일	
		@Configuration		: 스프링의 각종 설정을 잡는 어노테이션
		@EnableWebSecurity	: 스프링의 보안 설정을 담당 
		
		@Bean : 메소드 위에서 리턴받은 객체를 Spring IoC Container에 등록
				클래스 위에
					@Component, @Controller, @Service, @Repository
	 */

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			// http://localhost:9696/ 요청은 인증없이 접근을 허용
			.authorizeHttpRequests((authorizeHttpRequests)->authorizeHttpRequests
					.requestMatchers(new AntPathRequestMatcher("/**"))
					.permitAll()
					)

			// http://localhost:9696/h2-console 요청의 접근을 허용, H2 DB는  csrf 적용이 안되어서 설정
//			.csrf().disable()	// 모든 csrf를 사용하지 않도록 설정
			.csrf((csrf)->csrf
					.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
					)

			// H2 콘솔에서 프레임을 작동 시키도록 설정
			.headers((headers) -> headers
					.addHeaderWriter(new XFrameOptionsHeaderWriter
					(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
					)

			// 로그인을 처리하도록 설정 : controller에서 처리하지않고 Spring Security에서 처리하도록 설정
			// "/user/login" post 요청을 Security에서 처리
			
			// 로그인 처리 (Post 요청 : /user/login)
			.formLogin((formLogin)->formLogin
					.loginPage("/user/login")
					
					// SpringSecurity의 기본 설정 : ID의 name="username"
					//							password의 name="password"
//					.usernameParameter("email") 
//					.passwordParameter("password")
					
						// UserSecurityService.java 인증을 처리후 성공시
					.defaultSuccessUrl("/")
					)

			// 로그아웃 처리 (/user/logout), 세션에 적용된 모든 값을 제거
			// 로그아웃 완료시 "/" 이동됨 
			.logout((logout)->logout
					.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
					.logoutSuccessUrl("/")
					.invalidateHttpSession(true)
					)
			;

		return http.build();
	}

	@Bean	// Spring 컨테이너 (IoC 컨테이너) 객체를 등록
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// PasswordEncoder (인터페이스) ==> BCryptPasswordEncoder (구현한 클래스)

	// 인증을 처리하는 객체가 Bean(객체)등록 되어 있어야 한다 : IoC 컨테이너에 객체를 등록
		// UserSecurityService.java가 작동 되기 위한 Bean 등록
	@Bean
	AuthenticationManager authenticationManager
		(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		
		return authenticationConfiguration.getAuthenticationManager();
	}

}
