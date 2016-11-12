package org.ranceworks.nginxlog.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@PropertySource("classpath:/credentials.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value("${user.username}")
	private String user;
	@Value("${user.password}")
	private String password;

	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/css/signin.css", "/login").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login").defaultSuccessUrl("/index").and().rememberMe()
			.tokenValiditySeconds(2419200)
			.key("rememberMeKey");
	}


	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser(user).password(password).roles("USER");
	}

}
