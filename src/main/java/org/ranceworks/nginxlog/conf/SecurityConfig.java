package org.ranceworks.nginxlog.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * TODO to be implemented.
	 * see spring in action.
	 */
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			//.antMatchers("/css/**", "/index").permitAll()
			.antMatchers("/css/**", "/index").permitAll()
//			.antMatchers("/user/**").hasRole("USER")
			.and()
			.formLogin().loginPage("/login");
//		.formLogin().loginPage("/login").failureUrl("/login-error");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser("user").password("password").roles("USER");
	}
	/*
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
		auth.inMemoryAuthentication()
			.withUser("user").password("password").roles("USER");
	}
	*/
}
