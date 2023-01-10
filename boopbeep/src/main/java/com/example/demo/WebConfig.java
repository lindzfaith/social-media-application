package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true )
public class WebConfig extends WebSecurityConfigurerAdapter {
	/**
	 * The default authentication publisher (need this for securitycontext to handle/check authentication)
	 * @return default authentication event publisher
	 */
	@Bean
	DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher() {
		return new DefaultAuthenticationEventPublisher();
	}
	
	/**
	 * The password encoder used (to store and retrieve the passcode)
	 * @return
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * This is for MySQL (our datasource).
	 */
	@Autowired
	DataSource dataSource;
	
	/**
	 * Derived from Fima Taf's answer on this website: https://stackoverflow.com/questions/45909985/spring-boot-with-spring-security-login-form
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configure ( final AuthenticationManagerBuilder auth ) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
		.usersByUsernameQuery
		("select username, password, enabled from user where username=?")
		.authoritiesByUsernameQuery
		// roles do nothing at the moment; in the future, will implement admin who can delete accounts and posts
		("select user_username, roles from user_roles where user_username=?");
		
		auth.authenticationEventPublisher(defaultAuthenticationEventPublisher());
	}
	
	/**
	 * Allow requests to login without requiring authentication.
	 */
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		// Authorize any call related to login requests like GET and POST
		.antMatchers("/login*", "/login", "login").anonymous().anyRequest().authenticated()
		// Make this the default login page and success redirection link
		.and().formLogin().loginPage("/login").defaultSuccessUrl("/").and().logout().logoutUrl("/logout").logoutSuccessUrl("/login")
		// You need this for AngularJS.
		// More info here: https://docs.spring.io/spring-security/site/docs/4.2.15.RELEASE/apidocs/org/springframework/security/web/csrf/CookieCsrfTokenRepository.html
		.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}
	
	/**
	 * You need this to be able to make a call to users to create an account. 
	 */
	@Override 
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/users");
	}

}
