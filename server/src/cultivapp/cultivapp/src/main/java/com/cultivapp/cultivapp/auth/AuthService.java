package com.cultivapp.cultivapp.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service @RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepo;
    private final JwtService jwt;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public LoginResponse login(String email, String password) {
        var user = userRepo.findByEmail(email)
                .orElseThrow(() -> new AuthException("Credenciales inválidas"));

        if (!user.isEnabled()) {
            throw new DisabledException("Cuenta deshabilitada, contacte al administrador");
        }
        if (!encoder.matches(password, user.getPasswordHash())) {
            throw new AuthException("Credenciales inválidas");
        }
        String token = jwt.generate(user.getEmail(), Map.of("role", user.getRole().name()));
        return new LoginResponse(token, user.getRole().name());
    }

    // DTOs & Exceptions
    public record LoginRequest(String email, String password) {}
    public record LoginResponse(String token, String role) {}

    public static class AuthException extends RuntimeException {
        public AuthException(String m){ super(m); }
    }
    public static class DisabledException extends RuntimeException {
        public DisabledException(String m){ super(m); }
    }
}
