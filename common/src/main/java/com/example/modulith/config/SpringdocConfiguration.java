package com.example.modulith.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
    name = "Bearer Auth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer"
)
public class SpringdocConfiguration {
    @Bean
    public GroupedOpenApi allOpenApi() {
        return GroupedOpenApi.builder().group("all").displayName("ALL").pathsToMatch("/**").build();
    }
}
