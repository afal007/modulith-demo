package com.example.modulith.demo.bootstrap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingFilterConfiguration {
    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter commonsRequestLoggingFilter = new CommonsRequestLoggingFilter();
        commonsRequestLoggingFilter.setIncludePayload(true);
        commonsRequestLoggingFilter.setIncludeHeaders(false);
        commonsRequestLoggingFilter.setIncludeQueryString(true);
        commonsRequestLoggingFilter.setMaxPayloadLength(10000);
        commonsRequestLoggingFilter.setAfterMessagePrefix("REQUEST DATA: [");
        return commonsRequestLoggingFilter;
    }
}
