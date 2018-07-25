package com.rabbit.swaggersample.configs;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Configuration(value = "securitySecureConfig")
@EnableWebSecurity
public class SecuritySecureConfig extends WebSecurityConfigurerAdapter {

	static String ACTUATOR_URL_MATCHER = "/actuator/**";

	static String SWAGGER_UI_URL_MATCHER = "/swagger*";

	static String SWAGGER_DOCS_URL_MATCHER = "/v2/api-docs";

	static String SAMPLE_URL_MATCHER = "/sample/**";

	static String SECRET_URL_MATCHER = "/secret/**";

	/*
	 * PLEASE NOTE: For sake of simplicity we are omitting specific security configurations
	 */
	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		log.debug("Loading SECURE security config...");

		http
				.authorizeRequests()
				.mvcMatchers(ACTUATOR_URL_MATCHER, SWAGGER_UI_URL_MATCHER, SWAGGER_DOCS_URL_MATCHER).authenticated()
				// .anyRequest().authenticated()
				.mvcMatchers(SAMPLE_URL_MATCHER, SECRET_URL_MATCHER).permitAll()
				// .anyRequest().permitAll()

				.and()
				.httpBasic()

				.and()
				// .cors().disable()
				.cors()

				.and()
				.csrf().disable()
		;
	}

}
