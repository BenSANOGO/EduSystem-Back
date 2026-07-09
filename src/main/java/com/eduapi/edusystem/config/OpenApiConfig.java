package com.eduapi.edusystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI studentsManagementAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Students Management System API")
                        .description("REST API for managing students and classes.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Ben Sanogo")
                                .email("benyoussefsanogo@gmail.com"))
                        .license(new License()
                                .name("YESGroup License")));
    }
}