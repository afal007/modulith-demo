package com.example.modulith.demo.bootstrap;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
class SecurityConfiguration {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/actuator/**", "/actuator",
                "/openapi.yaml"
            )
            .requestMatchers(HttpMethod.OPTIONS);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MDCFilter mdcFilter) throws Exception {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new KeycloakGrantedAuthoritiesConverter());

        return http.addFilterAfter(mdcFilter, AuthorizationFilter.class)
            .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(c -> c.anyRequest().authenticated())
            .oauth2Client(c -> { })
            .oauth2ResourceServer(c -> c.jwt(ic -> ic.jwtAuthenticationConverter(converter)))
            .build();
    }

    private static class KeycloakGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
        @Override
        @SuppressWarnings("unchecked")
        public Collection<GrantedAuthority> convert(Jwt source) {
            Map<String, Object> realmAccess = source.getClaimAsMap("realm_access");
            List<String> roles = (List<String>) realmAccess.get("roles");
            return roles.stream().map(rn -> new SimpleGrantedAuthority("ROLE_" + rn)).collect(Collectors.toList());
        }
    }
}
