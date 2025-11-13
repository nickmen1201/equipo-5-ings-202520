package com.cultivapp.cultivapp.smokeTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Smoke Test 1: Login (Endpoint de Autenticación)
 * 
 * Qué flujo cubre: Validación de credenciales y generación de token JWT
 * Por qué es crítico: Sin autenticación funcionando, ningún usuario puede acceder a la aplicación
 * Tipo de test: Integration - Prueba controller, service, repository, database y JWT generation
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Smoke Test 1: Autenticación (Login)")
class LoginSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("1.1 - Login exitoso con credenciales válidas retorna token JWT")
    void testLoginSuccessful() throws Exception {
        String loginRequest = """
            {
                "email": "productor@cultivapp.com",
                "password": "password"
            }
            """;

        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isString())
                .andExpect(jsonPath("$.email").value("productor@cultivapp.com"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.rol").exists())
                .andExpect(jsonPath("$.password").doesNotExist()) // No debe retornar contraseña
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        System.out.println("✓ Login exitoso - Token JWT generado correctamente");
        System.out.println("  Response: " + responseBody.substring(0, Math.min(100, responseBody.length())) + "...");
    }

    @Test
    @DisplayName("1.2 - Token JWT tiene estructura correcta (header.payload.signature)")
    void testJwtTokenStructure() throws Exception {
        String loginRequest = """
            {
                "email": "productor@cultivapp.com",
                "password": "password"
            }
            """;

        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(matchesPattern("^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+$")))
                .andReturn();

        System.out.println("✓ Token JWT tiene estructura correcta (3 partes separadas por puntos)");
    }

    @Test
    @DisplayName("1.3 - Login con credenciales inválidas retorna 401 Unauthorized")
    void testLoginWithInvalidCredentials() throws Exception {
        String loginRequest = """
            {
                "email": "productor@cultivapp.com",
                "password": "wrongpassword"
            }
            """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isUnauthorized());

        System.out.println("✓ Credenciales inválidas correctamente rechazadas (401)");
    }

    @Test
    @DisplayName("1.4 - Login con email inexistente retorna 404 Not Found")
    void testLoginWithNonExistentEmail() throws Exception {
        String loginRequest = """
            {
                "email": "noexiste@cultivapp.com",
                "password": "password"
            }
            """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isNotFound());

        System.out.println("✓ Usuario inexistente correctamente rechazado (404)");
    }

    @Test
    @DisplayName("1.5 - Login con datos incompletos retorna 400 Bad Request")
    void testLoginWithMissingFields() throws Exception {
        String loginRequest = """
            {
                "email": "productor@cultivapp.com"
            }
            """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isBadRequest());

        System.out.println("✓ Datos incompletos correctamente rechazados (400)");
    }
}
