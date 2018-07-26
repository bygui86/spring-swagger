package com.rabbit.swaggersample.configs;

import com.rabbit.swaggersample.constants.SpringProfileConstants;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.TokenEndpoint;
import springfox.documentation.service.TokenRequestEndpoint;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PROTECTED)
@Profile(SpringProfileConstants.SPRING_PROFILE_SWAGGER_AUTH_OAUTH)
@Configuration(value = "swaggerOauthConfig")
@AutoConfigureBefore(SwaggerApiConfig.class)
public class SwaggerOauthConfig {

	static final String SWAGGER_OAUTH_SECURITY_REFERENCE = "spring-oauth";

	@Autowired
	@Qualifier("swaggerConfig")
	SwaggerConfig swaggerConfig;

	@Bean
	public SecurityConfiguration security() {

		final String clientId = getSwaggerConfig().getSwaggerOauthClientId();
		final String clientSecret = getSwaggerConfig().getSwaggerOauthClientSecret();

		log.debug("Loading SWAGGER-SECURITY-CONFIG: clientId {}, clientSecret {}",
				clientId, clientSecret);

		return SecurityConfigurationBuilder.builder()
				.clientId(clientId)
				.clientSecret(clientSecret)
				.scopeSeparator(" ")
				.useBasicAuthenticationWithAccessCodeGrant(true)
				.build();
	}

	@Bean("swaggerSecuritySchemes")
	public List<SecurityScheme> securitySchemes() {

		final GrantType grantType = new AuthorizationCodeGrantBuilder()
				.tokenEndpoint(
						new TokenEndpoint(
								getSwaggerConfig().getSwaggerOauthTokenUrl(),
								getSwaggerConfig().getSwaggerOauthTokenName()
						)
				)
				.tokenRequestEndpoint(
						new TokenRequestEndpoint(
								getSwaggerConfig().getSwaggerOauthAuthorizeUrl(),
								getSwaggerConfig().getSwaggerOauthClientId(),
								getSwaggerConfig().getSwaggerOauthClientSecret()
						)
				)
				.build();


		final SecurityScheme oauth = new OAuthBuilder()
				.name(SWAGGER_OAUTH_SECURITY_REFERENCE)
				.grantTypes(
						Collections.singletonList(grantType)
				)
				.scopes(
						Arrays.asList(authorizationScopes())
				)
				.build();

		return Collections.singletonList(oauth);
	}

	@Bean("swaggerSecurityContexts")
	public List<SecurityContext> securityContexts() {

		return Collections.singletonList(
				SecurityContext.builder()
						.securityReferences(
								Collections.singletonList(
										new SecurityReference(
												SWAGGER_OAUTH_SECURITY_REFERENCE,
												authorizationScopes())
								)
						)
						.forPaths(
								PathSelectors.regex(
										getSwaggerConfig().getSwaggerPathSelector()
								)
						)
						.build()
		);
	}

	private AuthorizationScope[] authorizationScopes() {

		return new AuthorizationScope[]{
				new AuthorizationScope("read", "for read operations"),
				new AuthorizationScope("write", "for write operations"),
				new AuthorizationScope("sample", "Access sample API") // not really good this manual configuration!
		};
	}

}
