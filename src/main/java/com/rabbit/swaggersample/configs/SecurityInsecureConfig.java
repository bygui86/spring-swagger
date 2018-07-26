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
@Profile(SpringProfileConstants.SPRING_PROFILE_SECURITY_INSECURE)
@Configuration(value = "securitySecureConfig")
@EnableWebSecurity
public class SecurityInsecureConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		log.debug("Loading INSECURE security config...");

		http
				.authorizeRequests()
				.anyRequest().permitAll()

				.and()
				.httpBasic()

				.and()
				.cors()

				.and()
				.csrf().disable()
		;
	}

}
