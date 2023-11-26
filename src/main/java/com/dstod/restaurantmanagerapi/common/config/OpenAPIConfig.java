package com.dstod.restaurantmanagerapi.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("Restaurant Manager API")
                        .contact(new Contact()
                                .name("Dimitar Todorov")
                                .email("d.todorov.vn@gmail.com")
                                .url("www.codesharing.org")
                        )
                        .description("This API is made to be consumed by Web and Mobile front-end applications")
                        .version("v0.1")
        );
    }
}
