package com.dstod.restaurantmanagerapi.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(servers = @Server(url = "https://codesharing.org/restaurant"))
public class OpenAPIConfig {

    @Value("${api.info.security.scheme.name}")
    private String securitySchemeNameProperty;
    @Value("${api.info.security.scheme.type}")
    private String securitySchemeTypeProperty;
    @Value("${api.info.security.scheme.format}")
    private String securitySchemeFormatProperty;
    @Value("${api.info.title}")
    private String apiInfoTitleProperty;
    @Value("${api.info.contact.name}")
    private String apiInfoContactNameProperty;
    @Value("${api.info.contact.email}")
    private String apiInfoContactEmailProperty;
    @Value("${api.info.description}")
    private String apiInfoDescriptionProperty;
    @Value("${api.info.version}")
    private String apiInfoVersionProperty;

    @Bean
    public OpenAPI openAPI() {
        final String securitySchemeName = securitySchemeNameProperty;
        return new OpenAPI().info(
                        new Info()
                                .title(apiInfoTitleProperty)
                                .contact(new Contact()
                                        .name(apiInfoContactNameProperty)
                                        .email(apiInfoContactEmailProperty)
                                )
                                .description(apiInfoDescriptionProperty)
                                .version(apiInfoVersionProperty)
                )
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme(securitySchemeTypeProperty)
                                                .bearerFormat(securitySchemeFormatProperty)
                                )
                );
    }
}
