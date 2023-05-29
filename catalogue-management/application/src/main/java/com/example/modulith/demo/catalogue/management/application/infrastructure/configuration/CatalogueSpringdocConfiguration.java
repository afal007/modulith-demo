package com.example.modulith.demo.catalogue.management.application.infrastructure.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatalogueSpringdocConfiguration {

    @Bean
    public GroupedOpenApi catalogueOpenApi() {
        return GroupedOpenApi.builder()
            .group("catalogue")
            .displayName("Catalogue")
            .pathsToMatch("/v1/transportation-request-cards/**")
            .build();
    }

}
