package com.cultivapp.cultivapp.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security Configuration - Spring Security Setup (REQ-001: Login Authentication)
 * 
 * This configuration class sets up Spring Security for the CultivApp backend.
 * It defines which endpoints are public (no authentication required) and configures
 * the password encoding mechanism used for secure password storage.
 * 
 * Spring Security Features Configured:
 * - CSRF Protection: Disabled for REST API (stateless authentication with JWT).
 * - Endpoint Authorization: Defines which endpoints require authentication.
 * - Password Encoding: Configures BCrypt for secure password hashing.
 * - H2 Console: Enables access to H2 database console for development.
 * 
 * REQ-001 Security Requirements:
 * - /api/auth/login must be publicly accessible (no authentication required).
 * - Passwords must be hashed using BCrypt before storage.
 * - Future endpoints will be protected using JWT tokens (not yet implemented).
 * 
 * Security Principles:
 * - Defense in depth: Multiple layers of security (authentication, authorization, encryption).
 * - Least privilege: Only grant access to resources that are absolutely necessary.
 * - Fail securely: Default behavior is to deny access unless explicitly permitted.
 * 
 * @author CultivApp Team
 * @version 1.0 (REQ-001)
 */
@Configuration // Marks this as a Spring configuration class
public class SecurityConfig {

    /**
     * Configures the HTTP security filter chain for the application.
     * 
     * This method sets up Spring Security's filter chain, which intercepts all
     * HTTP requests and applies security rules. The filter chain determines:
     * - Which endpoints require authentication.
     * - Which endpoints are public (accessible without login).
     * - How authentication is performed (JWT, basic auth, etc.).
     * 
     * REQ-001 Configuration:
     * - /api/auth/login is public (must be accessible for users to log in).
     * - /h2/** is public (H2 console for development/debugging).
     * - All other endpoints are currently public (will be protected in future requirements).
     * 
     * CSRF (Cross-Site Request Forgery) Protection:
     * - Disabled for REST APIs using JWT authentication.
     * - CSRF protection is designed for session-based authentication (cookies).
     * - JWT tokens are stored in localStorage, not cookies, so CSRF is not a concern.
     * 
     * Future Enhancements (Post REQ-001):
     * - Add JWT authentication filter to validate tokens on protected endpoints.
     * - Change .anyRequest().permitAll() to .anyRequest().authenticated().
     * - Add role-based authorization (e.g., only ADMIN can access certain endpoints).
     * 
     * @param http HttpSecurity builder for configuring security rules.
     * @return SecurityFilterChain that will be used by Spring Security.
     * @throws Exception if configuration fails.
     */
    @Bean // Registers this method's return value as a Spring bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF protection for REST API (not needed for JWT-based authentication)
            .csrf(csrf -> csrf.disable())
            
            // Configure authorization rules for HTTP requests
            .authorizeHttpRequests(auth -> auth
                // Public endpoints (no authentication required)
                .requestMatchers("/api/auth/login", "/h2/**").permitAll()
                
                // All other endpoints (currently public, will be protected in future)
                // TODO (Future REQs): Change to .anyRequest().authenticated()
                .anyRequest().permitAll()
            )
            
            // Disable frame options to allow H2 console iframe (development only)
            .headers(h -> h.frameOptions(f -> f.disable()))
            
            // Enable basic HTTP authentication (optional, mainly for testing)
            .httpBasic(Customizer.withDefaults());
        
        // Build and return the configured security filter chain
        return http.build();
    }

    /**
     * Configures BCrypt password encoder bean.
     * 
     * This bean is used throughout the application for securely hashing passwords.
     * BCrypt is a password hashing function designed to be slow and computationally
     * expensive, making brute-force attacks impractical.
     * 
     * BCrypt Security Features:
     * - One-way hashing: Cannot reverse the hash to get the original password.
     * - Automatic salting: Each hash includes a random salt to prevent rainbow table attacks.
     * - Adaptive cost: Can be configured to become slower over time as hardware improves.
     * - Industry standard: Widely recognized as a secure password hashing algorithm.
     * 
     * REQ-001 Usage:
     * - AuthService uses this encoder to validate passwords during login.
     * - When creating users, passwords are hashed with this encoder before storage.
     * - Plain-text passwords are NEVER stored in the database.
     * 
     * Password Validation Process:
     * 1. User submits plain-text password during login.
     * 2. AuthService retrieves hashed password from database.
     * 3. encoder.matches(plainPassword, hashedPassword) compares them.
     * 4. If match, authentication succeeds; if not, authentication fails.
     * 
     * @return BCryptPasswordEncoder instance for password hashing and validation.
     */
    @Bean // Registers this method's return value as a Spring bean (singleton by default)
    public BCryptPasswordEncoder passwordEncoder(){ 
        return new BCryptPasswordEncoder(); 
    }
}
