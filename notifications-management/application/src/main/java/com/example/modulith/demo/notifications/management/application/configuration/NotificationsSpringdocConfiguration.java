package com.example.modulith.demo.notifications.management.application.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationsSpringdocConfiguration {

    @Bean
    public GroupedOpenApi notificationsOpenApi() {
        return GroupedOpenApi.builder()
            .group("notifications")
            .displayName("Notifications")
            .pathsToMatch("/**/notification-settings", "/**/notifications")
            .build();
    }

}
