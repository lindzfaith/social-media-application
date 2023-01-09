package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true )
public class WebConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher() {
		return new DefaultAuthenticationEventPublisher();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	DataSource source;
	
	/**
	 * Derived from Fima Taf's answer on this website: https://stackoverflow.com/questions/45909985/spring-boot-with-spring-security-login-form
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureAuth ( final AuthenticationManagerBuilder auth ) throws Exception {
		auth.jdbcAuthentication().dataSource(source).passwordEncoder(passwordEncoder())
		.usersByUsernameQuery("select username, password, enabled from user_roles where user_username=?")
		.authoritiesByUsernameQuery("select user_username, roles from user_roles where user_username=?").and().authenticationEventPublisher(defaultAuthenticationEventPublisher());
	}
	
	@Override
	protected void configure(final HttpSecurity h) throws Exception {
		// Authorize post requests to users (for creations)
		h.authorizeRequests().antMatchers(HttpMethod.POST, "/users").anonymous()
		// Authorize any call related to login requests like GET and POST
		.antMatchers("/login*").anonymous().anyRequest().authenticated()
		// Make this the default login page and success redirection link
		.and().formLogin().loginPage("/login").defaultSuccessUrl("/")
		// You need this for AngularJS.
		// More info here: https://docs.spring.io/spring-security/site/docs/4.2.15.RELEASE/apidocs/org/springframework/security/web/csrf/CookieCsrfTokenRepository.html
		.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}

}
