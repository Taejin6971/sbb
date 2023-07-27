package com.mysite.sbb.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService{

	// Spring Security에서 인증을 담당하는 서비스
	// UserDetailsService 인터페이스의 정의된 메소드를 오버라이딩해서 구현
	
	private final UserRepository userRepository;
	
	// 인증을 처리하는 메소드
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// login_form.html 에서 username, password 넘어오는 username을 인풋으로 받음.
		// DB에서 인풋으로 넘어오는 username 값을 DB에서 조회
		Optional<SiteUser> _siteuser = userRepository.findByusername(username);
		
		if (_siteuser.isEmpty()) {
			// DB에서 해당 username이 존재하지 않는 경우
				// 강제로 예외를 발생시켜 view 페이지에서 오류 메세지를 출력함.
			throw new UsernameNotFoundException("해당 사용자는 DB에서 찾을수 없습니다.");
		} 
		
		// _siteuser의 값이 비어있지 않으면 SiteUser를 꺼냄
		SiteUser siteUser = _siteuser.get();
		
		List<GrantedAuthority> authorities = new ArrayList();
			// GrantedAuthority 인터페이스 : 메소드 선언만 되어있음
				// SimpleGrantedAuthority : 선언만 된 메소드를 구현하는 클래스
		
		// 관리자/사용자 구분 처리
			// username의 값이 admin이라면		: 관리자 권한 부여
			// username의 값이 admin이 아니라면	: 일반사용자 권한 부여
		if ("admin".equals(username)) {
			// username 필드의 admin 이라면 관리자 권한을 부여 : ADMIN("ROLE_ADMIN")
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
			
		} else {
			// username 필드의 admin 이 아니라면 일반사용자 권한을 부여 : USER("ROLE_USER")
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));

		}
		
		// UserDetails : 인터페이스
		// User : UserDetails 을 구현하는 객체
		// User (username, password, 권한)
		return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
	}
	
}
