package com.worldmessages.messagemanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String API_TITLE = "World Messages API";
    private static final String API_DESCRIPTION = "Application using Spring, RabbitMQ, Docker and Kubernetes";
    private static final String API_VERSION = "1.0.0";
    private static final String API_CONTACT = "Sergio Banegas";
    private static final String CONTROLLER_BASE_PACKAGE = "com.worldmessages.messagemanager";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(CONTROLLER_BASE_PACKAGE))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(API_TITLE)
                .description(API_DESCRIPTION)
                .termsOfServiceUrl("")
                .version(API_VERSION)
                .contact(new Contact(API_CONTACT, "", ""))
                .build();
    }

}
