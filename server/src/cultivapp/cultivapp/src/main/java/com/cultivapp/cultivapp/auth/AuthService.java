package com.cultivapp.cultivapp.auth;



import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cultivapp.cultivapp.models.Usuario;
import com.cultivapp.cultivapp.models.enums.Roles;
import com.cultivapp.cultivapp.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

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
        return new LoginResponse(token, user.getRol().name(),user.getId());
    }

    public record LoginRequest(String email, String password) {}
    public record LoginResponse(String token, String role,Integer id) {}

    public static class AuthException extends RuntimeException {
        public AuthException(String m){ super(m); }
    }
    
    public static class DisabledException extends RuntimeException {
        public DisabledException(String m){ super(m); }
    }

    public Usuario register(String nombre, String apellido, String email, String password, String ciudad) {
        if (userRepo.findByEmail(email).isPresent()) {
            throw new EmailAlreadyUsedException("El correo ya está registrado");
        }

        Usuario usuario = Usuario.builder()
                .nombre(nombre)
                .apellido(apellido)
                .email(email)
                .password(encoder.encode(password)) 
                .rol(Roles.PRODUCTOR) 
                .activo(true)
                .ciudad(ciudad)
                .build();

        return userRepo.save(usuario);
    }

    // Excepciones custom
    public static class EmailAlreadyUsedException extends RuntimeException {
        public EmailAlreadyUsedException(String message) { super(message); }
    }


}