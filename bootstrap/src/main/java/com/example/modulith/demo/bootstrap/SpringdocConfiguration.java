package com.example.modulith.demo.bootstrap;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;

@Configuration
@SecuritySchemes(
    {
        @SecurityScheme(
            name = "Bearer Auth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer"
        ), @SecurityScheme(
        name = "OAuth 2.0", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(
        password = @OAuthFlow(
            tokenUrl = "http://localhost:8180/realms/modulith/protocol/openid-connect/token",
            refreshUrl = "http://localhost:8180/realms/modulith/protocol/openid-connect/token"
        )
    )
    )
    }
)
public class SpringdocConfiguration {
    @Bean
    public GroupedOpenApi allOpenApi() {
        return GroupedOpenApi.builder().group("all").displayName("ALL").pathsToMatch("/**").build();
    }
}
