package com.muvz.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.muvz.api.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(metaData())
				.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, responseMessageForGet())
				.globalResponses(HttpMethod.POST, responseMessageForPost())
				.globalResponses(HttpMethod.PUT, responseMessageForPut())
				.globalResponses(HttpMethod.DELETE, responseMessageForDelete());
				
	}
	
	public ApiInfo metaData() {
		return new ApiInfoBuilder()
				.title("API - Muvz")
				.description("Projeto API Spring - Muvz")
				.version("1.0.0")
				.license("Apache License Version 2.0")
				.licenseUrl("http://localhost:8080/swagger-ui/")
				.contact(contact()).build();
	}
	
	private Contact contact() {
		return new Contact("Fabricio Macedo", "https://github.com/fabriciio95",
				"fabriciousiqueira@gmail.com");
	}
	
	private List<Response> responseMessageForGet() {
		return new ArrayList<Response>() { 
			private static final long serialVersionUID = 1L;

			{
				add(new ResponseBuilder().code("200").description("Success").build());
				add(new ResponseBuilder().code("404").description("Not found").build());
				add(new ResponseBuilder().code("500").description("Internal server error").build());
		
			}
		};
	}
	
	private List<Response> responseMessageForPost() {
		return new ArrayList<Response>() { 
			private static final long serialVersionUID = 1L;

			{
				add(new ResponseBuilder().code("201").description("Created").build());
				add(new ResponseBuilder().code("404").description("Not found").build());
				add(new ResponseBuilder().code("500").description("Internal server error").build());
		
			}
		};
	}
	
	
	private List<Response> responseMessageForPut() {
		return new ArrayList<Response>() { 
			private static final long serialVersionUID = 1L;

			{
				add(new ResponseBuilder().code("200").description("Success").build());
				add(new ResponseBuilder().code("404").description("Not found").build());
				add(new ResponseBuilder().code("500").description("Internal server error").build());
		
			}
		};
	}
	
	private List<Response> responseMessageForDelete() {
		return new ArrayList<Response>() { 
			private static final long serialVersionUID = 1L;

			{
				add(new ResponseBuilder().code("204").description("No content").build());
				add(new ResponseBuilder().code("404").description("Not found").build());
				add(new ResponseBuilder().code("500").description("Internal server error").build());
		
			}
		};
	}

}
