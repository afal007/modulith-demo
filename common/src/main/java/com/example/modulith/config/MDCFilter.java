package com.example.modulith.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class MDCFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        String remoteAddr = request.getHeader("X-FORWARDED-FOR");
        if (remoteAddr == null || "".equals(remoteAddr)) {
            remoteAddr = request.getRemoteAddr();
        }

        String contextPath = request.getContextPath();
        String pathInfo = request.getPathInfo();
        String remoteHost = request.getRemoteHost();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = null;
        if (authentication instanceof JwtAuthenticationToken a) {
            name = Optional.of(a)
                .map(JwtAuthenticationToken::getToken)
                .map(jwt -> jwt.getClaimAsString("preferred_username"))
                .orElse(null);
        }

        try (
            MDC.MDCCloseable remoteAddrMdc = MDC.putCloseable("remoteAddr", remoteAddr);
            MDC.MDCCloseable remoteHostMdc = MDC.putCloseable("remoteHost", remoteHost);
            MDC.MDCCloseable contextPathMdc = MDC.putCloseable("contextPath", contextPath);
            MDC.MDCCloseable pathInfoMdc = MDC.putCloseable("pathInfo", pathInfo);
            MDC.MDCCloseable username = MDC.putCloseable("username", name)
        ) {
            filterChain.doFilter(request, response);
        }
    }
}
