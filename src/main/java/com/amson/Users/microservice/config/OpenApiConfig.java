package com.amson.Users.microservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI usersMicroserviceOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Users microservice")
						.version("0.0.1")
						.description("""
								Small read API backed by an embedded H2 in-memory database. \
								Swagger UI documents request parameters and response payloads; \
								authentication is not enabled in this revision.""")
						.contact(new Contact().name("Engineering").email("engineering@example.com"))
						.license(new License().name("Proprietary").url("https://example.com/license")));
	}

}
