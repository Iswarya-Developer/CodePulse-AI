package com.codepulse.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF (usually needed for stateless REST APIs)
                .csrf(csrf -> csrf.disable())

                // 2. Define endpoint authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/health").permitAll() // Allow anyone to hit health check
                        .anyRequest().authenticated()                  // All other requests still require authentication
                );

        return http.build();
    }
}