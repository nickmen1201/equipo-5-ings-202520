package com.cultivapp.cultivapp.auth;

import com.cultivapp.cultivapp.model.Usuario;
import com.cultivapp.cultivapp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Authentication Service (REQ-001: Login)
 * 
 * Validates credentials and generates JWT tokens:
 * 1. Find user by email
 * 2. Check if account is active
 * 3. Validate password (BCrypt)
 * 4. Generate JWT token with role
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UsuarioRepository userRepo;
    private final JwtService jwt;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public LoginResponse login(String email, String password) {
        var user = userRepo.findByEmail(email)
                .orElseThrow(() -> new AuthException("Credenciales inválidas"));

        if (!user.getActivo()) {
            throw new DisabledException("Cuenta deshabilitada, contacte al administrador");
        }
        
        if (!encoder.matches(password, user.getPassword())) {
            throw new AuthException("Credenciales inválidas");
        }
        
        String token = jwt.generate(user.getEmail(), Map.of("role", user.getRol().name()));
        return new LoginResponse(token, user.getRol().name());
    }

    public record LoginRequest(String email, String password) {}
    public record LoginResponse(String token, String role) {}

    public static class AuthException extends RuntimeException {
        public AuthException(String m){ super(m); }
    }
    
    public static class DisabledException extends RuntimeException {
        public DisabledException(String m){ super(m); }
    }
}
