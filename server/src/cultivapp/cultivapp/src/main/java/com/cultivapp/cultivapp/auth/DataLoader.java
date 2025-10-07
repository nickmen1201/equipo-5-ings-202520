package com.cultivapp.cultivapp.config;

import com.cultivapp.cultivapp.auth.Role;
import com.cultivapp.cultivapp.auth.User;
import com.cultivapp.cultivapp.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component @RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder;

    @Override public void run(String... args){
        if (repo.count() > 0) return;

        repo.save(User.builder()
                .email("user@demo.com")
                .passwordHash(encoder.encode("Password123"))
                .role(Role.PRODUCTOR)
                .enabled(true)
                .build());

        repo.save(User.builder()
                .email("admin@demo.com")
                .passwordHash(encoder.encode("Admin123"))
                .role(Role.ADMIN)
                .enabled(false) // para escenario "Cuenta deshabilitada"
                .build());
    }
}
