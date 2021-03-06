package com.rabbit.swaggersample.configs;

import com.rabbit.swaggersample.constants.SpringProfileConstants;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Profile(SpringProfileConstants.SPRING_PROFILE_SECURITY_SECURE)
@Configuration(value = "securitySecureConfig")
@EnableWebSecurity
public class SecuritySecureConfig extends WebSecurityConfigurerAdapter {

	static String ACTUATOR_URL_MATCHER = "/actuator/**";

	static String SWAGGER_UI_URL_MATCHER = "/swagger*";

	static String SWAGGER_DOCS_URL_MATCHER = "/v2/api-docs";

	static String SAMPLE_URL_MATCHER = "/sample/**";

	static String SECRET_URL_MATCHER = "/secret/**";

	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		log.debug("Loading SECURE security config...");

		http
				.authorizeRequests()
				.mvcMatchers(ACTUATOR_URL_MATCHER, SWAGGER_UI_URL_MATCHER, SWAGGER_DOCS_URL_MATCHER).authenticated()
				.mvcMatchers(SAMPLE_URL_MATCHER, SECRET_URL_MATCHER).permitAll()

				.and()
				.httpBasic()

				.and()
				.cors()

				.and()
				.csrf().disable()
		;
	}

}
