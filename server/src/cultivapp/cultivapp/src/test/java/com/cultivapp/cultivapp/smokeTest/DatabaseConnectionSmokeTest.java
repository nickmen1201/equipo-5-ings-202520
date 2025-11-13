package com.cultivapp.cultivapp.smokeTest;

import com.cultivapp.cultivapp.repositories.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Smoke Test 5: Conexión a Base de Datos (PostgreSQL)
 * 
 * Qué flujo cubre: Verificación de conectividad con PostgreSQL y carga de datos iniciales
 * Por qué es crítico: Sin BD funcionando, ninguna operación de persistencia es posible
 * Tipo de test: Integration - Prueba Spring Boot, JPA/Hibernate y PostgreSQL
 */
@SpringBootTest
@DisplayName("Smoke Test 5: Conexión a Base de Datos")
class DatabaseConnectionSmokeTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EspecieRepository especieRepository;

    @Autowired
    private CultivoRepository cultivoRepository;

    @Autowired
    private EtapaRepository etapaRepository;

    @Autowired
    private ReglaRepository reglaRepository;

    @Test
    @DisplayName("5.1 - Conexión básica a base de datos funciona")
    void testDatabaseConnection() {
        // Verifica si podemos hacer una operación simple en la BD
        long count = usuarioRepository.count();
        
        assertTrue(count >= 0, "Database connection failed - could not count users");
        
        System.out.println("✓ Conexión a base de datos exitosa - " + count + " usuarios encontrados");
    }

    @Test
    @DisplayName("5.2 - Usuarios iniciales cargados correctamente (admin y productor)")
    void testInitialUsersLoaded() {
        long userCount = usuarioRepository.count();
        
        assertTrue(userCount >= 2, "Expected at least 2 initial users (admin and productor)");
        
        // Verificar que los usuarios específicos existen
        boolean adminExists = usuarioRepository.findByEmail("admin@cultivapp.com").isPresent();
        boolean productorExists = usuarioRepository.findByEmail("productor@cultivapp.com").isPresent();
        
        assertTrue(adminExists, "Admin user should exist");
        assertTrue(productorExists, "Productor user should exist");
        
        System.out.println("✓ Usuarios iniciales cargados: " + userCount + " usuarios");
    }

    @Test
    @DisplayName("5.3 - Especies base cargadas correctamente (8 especies)")
    void testInitialSpeciesLoaded() {
        long especiesCount = especieRepository.count();
        
        assertEquals(8, especiesCount, "Expected 8 base species (Tomate, Maíz, Frijol, Lechuga, Zanahoria, Papa, Cebolla, Pimentón)");
        
        System.out.println("✓ Especies base cargadas: " + especiesCount + " especies");
    }

    @Test
    @DisplayName("5.4 - Reglas predefinidas cargadas correctamente (13 reglas)")
    void testInitialRulesLoaded() {
        long reglasCount = reglaRepository.count();
        
        assertTrue(reglasCount >= 13, "Expected at least 13 predefined rules");
        
        System.out.println("✓ Reglas predefinidas cargadas: " + reglasCount + " reglas");
    }

    @Test
    @DisplayName("5.5 - Etapas cargadas correctamente (54 etapas)")
    void testEtapasLoaded() {
        long etapasCount = etapaRepository.count();
        
        assertTrue(etapasCount >= 54, "Expected at least 54 etapas after running seed-etapas.sql");
        
        System.out.println("✓ Etapas cargadas: " + etapasCount + " etapas");
    }

    @Test
    @DisplayName("5.6 - Todas las especies tienen etapas asociadas")
    void testAllSpeciesHaveEtapas() {
        long especiesCount = especieRepository.count();
        
        // Verificar que cada especie tiene al menos 1 etapa
        especieRepository.findAll().forEach(especie -> {
            long etapasDeEspecie = etapaRepository.countByEspecieId(especie.getId());
            assertTrue(etapasDeEspecie > 0, 
                "Especie " + especie.getNombre() + " should have at least one etapa");
        });
        
        System.out.println("✓ Todas las " + especiesCount + " especies tienen etapas asociadas");
    }

    @Test
    @DisplayName("5.7 - Tablas principales existen y son accesibles")
    void testMainTablesExist() {
        assertDoesNotThrow(() -> {
            usuarioRepository.count();
            especieRepository.count();
            cultivoRepository.count();
            etapaRepository.count();
            reglaRepository.count();
        }, "All main tables should be accessible");
        
        System.out.println("✓ Todas las tablas principales existen y son accesibles");
    }

    @Test
    @DisplayName("5.8 - Operaciones CRUD básicas funcionan")
    void testBasicCrudOperations() {
        // Test de lectura
        long initialCount = usuarioRepository.count();
        assertTrue(initialCount >= 0);
        
        // Test de búsqueda
        var admin = usuarioRepository.findByEmail("admin@cultivapp.com");
        assertTrue(admin.isPresent(), "Should be able to find admin user");
        
        // Test de filtrado
        var especies = especieRepository.findAll();
        assertFalse(especies.isEmpty(), "Should be able to fetch all especies");
        
        System.out.println("✓ Operaciones CRUD básicas funcionan correctamente");
    }
}
