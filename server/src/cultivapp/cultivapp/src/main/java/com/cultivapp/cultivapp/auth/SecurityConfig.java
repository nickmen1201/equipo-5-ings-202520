package com.cultivapp.cultivapp.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security Configuration (REQ-001: Login)
 * 
 * - Public endpoints: /api/auth/login, /h2/**
 * - CSRF disabled (REST API with JWT)
 * - BCrypt for password hashing
 * - TODO: Protect other endpoints with JWT validation
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/login", "/h2/**").permitAll()
                .anyRequest().permitAll() // TODO: Change to .authenticated() when JWT filter added
            )
            .headers(h -> h.frameOptions(f -> f.disable()))
            .httpBasic(Customizer.withDefaults());
        
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){ 
        return new BCryptPasswordEncoder(); 
    }
}
