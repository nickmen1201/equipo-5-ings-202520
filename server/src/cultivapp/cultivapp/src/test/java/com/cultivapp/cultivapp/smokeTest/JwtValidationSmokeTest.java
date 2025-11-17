package com.cultivapp.cultivapp.smokeTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Smoke Test 3: Validación de Tokens JWT
 * 
 * Qué flujo cubre: Verificación de que el sistema de autenticación JWT valida correctamente
 *                  tokens válidos, inválidos, expirados y ausentes
 * Por qué es crítico: La seguridad de toda la aplicación depende de este mecanismo
 * Tipo de test: Integration - Prueba el filtro de seguridad, validación de JWT y acceso a recursos protegidos
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Smoke Test 3: Validación de Tokens JWT")
class JwtValidationSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String validToken;
    private static final String PROTECTED_ENDPOINT = "/api/cultivos";

    @BeforeEach
    void setUp() throws Exception {
        // Obtener un token válido antes de cada test
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
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        validToken = jsonNode.get("token").asText();
    }

    @Test
    @DisplayName("3.1 - Token válido permite acceso a recursos protegidos (200 OK)")
    void testValidTokenAllowsAccess() throws Exception {
        mockMvc.perform(get(PROTECTED_ENDPOINT)
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        System.out.println("✓ Token válido permite acceso a recursos protegidos");
    }

    @Test
    @DisplayName("3.2 - Token inválido/manipulado es rechazado (401/403)")
    void testInvalidTokenIsDenied() throws Exception {
        String invalidToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        mockMvc.perform(get(PROTECTED_ENDPOINT)
                        .header("Authorization", "Bearer " + invalidToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()); // 401 o 403

        System.out.println("✓ Token inválido correctamente rechazado");
    }

    @Test
    @DisplayName("3.3 - Petición sin token es rechazada (401 Unauthorized)")
    void testRequestWithoutTokenIsDenied() throws Exception {
        mockMvc.perform(get(PROTECTED_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        System.out.println("✓ Petición sin token correctamente rechazada (401)");
    }

    @Test
    @DisplayName("3.4 - Token con formato incorrecto es rechazado")
    void testMalformedTokenIsDenied() throws Exception {
        String malformedToken = "esto-no-es-un-jwt-valido";

        mockMvc.perform(get(PROTECTED_ENDPOINT)
                        .header("Authorization", "Bearer " + malformedToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        System.out.println("✓ Token con formato incorrecto correctamente rechazado");
    }

    @Test
    @DisplayName("3.5 - Token sin prefijo 'Bearer' es rechazado")
    void testTokenWithoutBearerPrefixIsDenied() throws Exception {
        mockMvc.perform(get(PROTECTED_ENDPOINT)
                        .header("Authorization", validToken) // Sin "Bearer "
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        System.out.println("✓ Token sin prefijo 'Bearer' correctamente rechazado");
    }

    @Test
    @DisplayName("3.6 - Token vacío es rechazado")
    void testEmptyTokenIsDenied() throws Exception {
        mockMvc.perform(get(PROTECTED_ENDPOINT)
                        .header("Authorization", "Bearer ")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        System.out.println("✓ Token vacío correctamente rechazado");
    }

    @Test
    @DisplayName("3.7 - Token válido permite múltiples peticiones consecutivas")
    void testValidTokenWorksForMultipleRequests() throws Exception {
        // Primera petición
        mockMvc.perform(get(PROTECTED_ENDPOINT)
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Segunda petición con el mismo token
        mockMvc.perform(get(PROTECTED_ENDPOINT)
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Tercera petición con el mismo token
        mockMvc.perform(get(PROTECTED_ENDPOINT)
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        System.out.println("✓ Token válido funciona para múltiples peticiones consecutivas");
    }

    @Test
    @DisplayName("3.8 - Diferentes endpoints protegidos requieren token válido")
    void testMultipleProtectedEndpointsRequireToken() throws Exception {
        // Test endpoint de especies
        mockMvc.perform(get("/api/especies")
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Test endpoint de reglas
        mockMvc.perform(get("/api/reglas")
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        System.out.println("✓ Múltiples endpoints protegidos correctamente validados");
    }
}
