package com.example.modulith.demo.cargo.management.application.infrastructure.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransportationRequestsSpringdocConfiguration {

    @Bean
    public GroupedOpenApi transportationRequestsOpenApi() {
        return GroupedOpenApi.builder()
            .group("transportation-requests")
            .displayName("Transportation Requests")
            .pathsToMatch("/v1/transportation-requests/**")
            .build();
    }

}
