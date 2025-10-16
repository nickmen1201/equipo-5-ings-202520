package com.cultivapp.cultivapp.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom handler for access denied exceptions (403 Forbidden).
 * Implements REQ-003 requirement for unauthorized access responses.
 */

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    
    private static final Logger log = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    private final ObjectMapper objectMapper;
    
    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        String requestUri = request.getRequestURI();
        String userEmail = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "anonymous";

        log.warn("Access denied for user '{}' attempting to access: {} - Reason: {}",
                userEmail, requestUri, accessDeniedException.getMessage());

        try {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", LocalDateTime.now().toString());
            errorResponse.put("status", HttpStatus.FORBIDDEN.value());
            errorResponse.put("error", "Forbidden");
            errorResponse.put("message", "No autorizado");
            errorResponse.put("path", requestUri);

            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            response.getWriter().flush();

            log.debug("Access denied response sent successfully for user: {}", userEmail);

        } catch (IOException e) {
            log.error("Error writing access denied response: {}", e.getMessage(), e);
            throw e;
        }
    }
}