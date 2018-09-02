package com.adidas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
// @Import({BasicAuthenticationEntryPoint.class})
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	// private BasicAuthenticationEntryPoint authEntryPoint;
	AuthenticationEntryPoint authEntryPoint;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, "/review/**").authenticated()
				.antMatchers(HttpMethod.PATCH, "/review/**").authenticated()
				.antMatchers(HttpMethod.DELETE, "/review/**").authenticated().anyRequest().permitAll().and().httpBasic()
				.authenticationEntryPoint(authEntryPoint);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(encoder()).withUser("manish")
				.password("$2a$04$Q32E83Qa3MHn6UcadfygyehADtRzpoXAk8vvoxFur/UEDHbgmgR6S").roles("USER");
	}
	/* https://www.devglan.com/online-tools/bcrypt-hash-generator */

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
