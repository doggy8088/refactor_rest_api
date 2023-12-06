package com.example.demo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * This is the Swagger class to generate the API documentation
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final Contact DEFAULT_CONTACT = new Contact("DEFAULT_CONTACT", "DEFAULT_URL", "DEFAULT_EMAIL");
    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Product Api Documentation",
            "Description about Products and its Options",
            "1.0", "urn:tos", DEFAULT_CONTACT,
            "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");

    private Set<String> DEFAULT_CONSUMES_PRODUCES = new HashSet<>(Arrays.asList("application/json", "application/xml"));

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .consumes(DEFAULT_CONSUMES_PRODUCES)
                .produces(DEFAULT_CONSUMES_PRODUCES);
    }
}
