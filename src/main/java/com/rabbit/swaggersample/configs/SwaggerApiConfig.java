package com.rabbit.swaggersample.configs;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PROTECTED)
@Configuration(value = "swaggerApiConfig")
@EnableSwagger2
public class SwaggerApiConfig {

	@Autowired
	@Qualifier("swaggerConfig")
	SwaggerConfig swaggerConfig;

	@Autowired(required = false)
	@Qualifier("swaggerSecuritySchemes")
	List<SecurityScheme> swaggerSecuritySchemes;

	@Autowired(required = false)
	@Qualifier("swaggerSecurityContexts")
	List<SecurityContext> swaggerSecurityContexts;

	/**
	 * Swagger2 API documentation setup
	 *
	 * @return {@link Docket}
	 */
	@Bean
	public Docket api() {

		log.debug("Loading SWAGGER-GENERAL-CONFIG: package {} path {} version {}...",
				getSwaggerConfig().getSwaggerPackageName(), getSwaggerConfig().getSwaggerPathSelector(), getSwaggerConfig().getSwaggerApiVersion());

		return new Docket(DocumentationType.SWAGGER_2)
				// Globally overriding response messages of HTTP methods
				.useDefaultResponseMessages(false)
				.globalResponseMessage(
						RequestMethod.GET,
						globalResponseMessage()
				)

				// Custom API scan and inclusion/exclusion
				.select()
				.apis(
						RequestHandlerSelectors.basePackage(
								getSwaggerConfig().getSwaggerPackageName()
						)
				)
				.paths(
						PathSelectors.regex(
								getSwaggerConfig().getSwaggerPathSelector()
						)
				)
				.build()

				// Custom API info
				.apiInfo(
						apiInfo()
				)

				// Security
				.securitySchemes(
						getSwaggerSecuritySchemes()
				)
				.securityContexts(
						getSwaggerSecurityContexts()
				)
				;
	}

	private List<ResponseMessage> globalResponseMessage() {

		final List<ResponseMessage> globalResponseMessages = new ArrayList<>();
		globalResponseMessages.add(
				new ResponseMessageBuilder()
						.code(500)
						.message(
								getSwaggerConfig().getSwaggerGlobal500errorMsg()
						)
						.responseModel(
								new ModelRef("Error")
						)
						.build()
		);
		globalResponseMessages.add(
				new ResponseMessageBuilder()
						.code(403)
						.message(
								getSwaggerConfig().getSwaggerGlobal403errorMsg()
						)
						.build()
		);
		return globalResponseMessages;
	}

	private ApiInfo apiInfo() {

		return new ApiInfo(
				getSwaggerConfig().getSwaggerApiInfoTitle(),
				getSwaggerConfig().getSwaggerApiInfoDescr(),
				getSwaggerConfig().getSwaggerApiVersion(),
				getSwaggerConfig().getSwaggerApiInfoTermServUrl(),
				new Contact(
						getSwaggerConfig().getSwaggerApiInfoContactName(),
						getSwaggerConfig().getSwaggerApiInfoContactUrl(),
						getSwaggerConfig().getSwaggerApiInfoContactEmail()
				),
				getSwaggerConfig().getSwaggerApiInfoLicense(),
				getSwaggerConfig().getSwaggerApiInfoLicenseUrl(),
				Collections.emptyList() // vendorExtension list
		);
	}

}
