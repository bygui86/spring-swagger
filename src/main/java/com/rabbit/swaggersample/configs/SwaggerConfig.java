package com.rabbit.swaggersample.configs;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Configuration(value = "swaggerConfig")
public class SwaggerConfig {

	/* GENERAL */

	@Value("${swagger2.package.name:com.rabbit.swaggersample}")
	String swaggerPackageName;

	@Value("${swagger2.path.selector:/sample/*}")
	String swaggerPathSelector;

	@Value("${swagger2.api.version}")
	String swaggerApiVersion;

	/* GLOBAL ERRORS */

	@Value("${swagger2.response.global.500.error.message}")
	String swaggerGlobal500errorMsg;

	@Value("${swagger2.response.global.403.error.message}")
	String swaggerGlobal403errorMsg;

	/* API INFO */

	@Value("${swagger2.api.info.title}")
	String swaggerApiInfoTitle;

	@Value("${swagger2.api.info.description}")
	String swaggerApiInfoDescr;

	@Value("${swagger2.api.info.terms.service.url}")
	String swaggerApiInfoTermServUrl;

	@Value("${swagger2.api.info.contact.name}")
	String swaggerApiInfoContactName;

	@Value("${swagger2.api.info.contact.url}")
	String swaggerApiInfoContactUrl;

	@Value("${swagger2.api.info.contact.email}")
	String swaggerApiInfoContactEmail;

	@Value("${swagger2.api.info.license}")
	String swaggerApiInfoLicense;

	@Value("${swagger2.api.info.license.url}")
	String swaggerApiInfoLicenseUrl;

	/* SECURITY - OAUTH */

	@Value("${swagger2.security.oauth.server}")
	String swaggerOauthServer;

	@Value("${swagger2.security.oauth.token.url}")
	String swaggerOauthTokenUrl;

	@Value("${swagger2.security.oauth.token.name}")
	String swaggerOauthTokenName;

	@Value("${swagger2.security.oauth.authorize.url}")
	String swaggerOauthAuthorizeUrl;

	@Value("${swagger2.security.oauth.client.id}")
	String swaggerOauthClientId;

	@Value("${swagger2.security.oauth.client.secret}")
	String swaggerOauthClientSecret;

}
