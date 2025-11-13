package com.cultivapp.cultivapp.smokeTest;

import com.cultivapp.cultivapp.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DatabaseConnectionSmokeTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void testDatabaseConnection() {
        //Aca verifica si podemos hacer una operación simple en la BD
        long count = usuarioRepository.count();


        assertTrue(count >= 0, "Database connection failed - could not count users");

        System.out.println("Database connection OK - Found " + count + " users");
    }
}
