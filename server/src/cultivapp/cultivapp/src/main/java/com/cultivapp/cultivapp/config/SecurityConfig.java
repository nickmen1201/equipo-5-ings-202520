package com.cultivapp.cultivapp.config;


import com.cultivapp.security.CustomAccessDeniedHandler;
import com.cultivapp.security.CustomAuthenticationEntryPoint;
import com.cultivapp.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration for CultivApp.
 * Implements role-based access control (RBAC) for Admin and Producer roles.
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    /**
     * Configures the security filter chain with role-based access control.
     *
     * Security Rules:
     * - /especies/** endpoints: ADMIN role only
     * - /tareas/** endpoints: ADMIN role only
     * - /api/auth/** endpoints: Public access
     *
     * @param http HttpSecurity configuration
     * @return SecurityFilterChain configured security chain
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Configuring security filter chain for CultivApp");

        try {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(cors -> cors.configure(http))
                    .sessionManagement(session ->
                            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                    .authorizeHttpRequests(auth -> auth
                            // Public endpoints
                            .requestMatchers("/api/auth/**", "/api/public/**").permitAll()
                            .requestMatchers("/h2-console/**").permitAll()

                            // Admin-only endpoints - REQ-003
                            // Species management
                            .requestMatchers(HttpMethod.GET, "/especies/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/especies/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/especies/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/especies/**").hasRole("ADMIN")

                            // Task management
                            .requestMatchers(HttpMethod.GET, "/tareas/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/tareas/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/tareas/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/tareas/**").hasRole("ADMIN")

                            // Producer endpoints
                            .requestMatchers("/api/producer/**").hasRole("PRODUCTOR")

                            // Any other request must be authenticated
                            .anyRequest().authenticated()
                    )

                    .exceptionHandling(exception -> exception
                            .accessDeniedHandler(accessDeniedHandler)
                            .authenticationEntryPoint(authenticationEntryPoint)
                    )

                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

            // H2 Console frame options
            http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

            log.info("Security filter chain configured successfully");
            return http.build();

        } catch (Exception e) {
            log.error("Error configuring security filter chain: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to configure security", e);
        }
    }

    /**
     * Password encoder bean using BCrypt hashing algorithm.
     *
     * @return PasswordEncoder BCrypt password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.debug("Creating BCrypt password encoder bean");
        return new BCryptPasswordEncoder();
    }
}