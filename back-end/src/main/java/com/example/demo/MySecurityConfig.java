package com.example.demo;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/").permitAll()
//				.antMatchers("/accounts").hasRole("VIP1")
//				.antMatchers("/level").hasRole("VIP2")
//				.antMatchers("/books").hasRole("VIP1");
//		
//		http.formLogin()
//		.usernameParameter("id")
//		.passwordParameter("passowrd")
//		.loginPage("/accounts/login");
	}
}
