package com.jukeboxapp.jukebox.springconfig;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${service.version-swagger}")
	private String versionService;

	public @Bean Docket restApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.any())
				.paths(paths()).build();
	}

	@SuppressWarnings("unchecked")
	private Predicate<String> paths() {
		return or(regex("/api/jukeboxapp/v1.*"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("REST Api Service to remote control jukeboxes settings")
				.description("API for managing settings for jukeboxes").termsOfServiceUrl("")
				.license("https://www.jukeboxapp.com").licenseUrl("").version(versionService).build();
	}

}
