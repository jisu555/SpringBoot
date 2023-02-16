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

@Configuration			//Security의 설정을 적용하는 어노테이션
@EnableWebSecurity		//http의 URL 접근을 제어하는 어노테이션 
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests().requestMatchers(
				new AntPathRequestMatcher("/**")).permitAll()
			.and()
				.csrf().ignoringRequestMatchers(
						new AntPathRequestMatcher("/h2-console/**"))
			.and()
				.headers()
				.addHeaderWriter(new XFrameOptionsHeaderWriter(
						XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
				
			//Spring Security 로그인 처리부분	
			.and()
				.formLogin()
				.loginPage("/user/login")
				.defaultSuccessUrl("/")	//로그인 성공 시 세션에 로그인 정보를 담고 /(루트페이지)로 이동
				
			//Spring Security 로그아웃 처리부분	
			.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)	//세션의 담긴 모든 값을 삭제하라.
		;
		
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManger(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	/*
	AuthenticationManager 빈을 생성했다.
	AuthenticationManager는 스프링 시큐리티의 인증을 담당한다.
	AuthenticationManager 빈 생성 시 스프링의 내부 동작으로 인해 위에서 작성한
	UserSecurityService와 PasswordEncoder가 자동으로 설정된다.
	*/
	
	
	
}
