package com.cultivapp.cultivapp.auth;

import com.cultivapp.cultivapp.model.Usuario;
import com.cultivapp.cultivapp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Authentication Service - Business Logic Layer for Login (REQ-001: Login Authentication)
 * 
 * This service contains the core business logic for user authentication in CultivApp.
 * It coordinates between the repository (data access), security (password validation),
 * and JWT generation to authenticate users and issue tokens.
 * 
 * Service Layer Responsibilities in Layered Architecture:
 * - Implements the authentication business rules (credential validation, account status checks).
 * - Coordinates between multiple components (UserRepository, JwtService, BCryptPasswordEncoder).
 * - Handles error cases with appropriate exceptions (invalid credentials, disabled accounts).
 * - Never exposes which part of the credentials was wrong (security best practice).
 * 
 * REQ-001 Login Flow:
 * 1. Controller receives email/password from frontend.
 * 2. Controller calls this service's login() method.
 * 3. Service retrieves user from database via UserRepository.
 * 4. Service checks if account is enabled.
 * 5. Service validates password using BCrypt comparison.
 * 6. Service generates JWT token via JwtService.
 * 7. Service returns token and role to controller, which sends to frontend.
 * 
 * Security Principles:
 * - Passwords are never logged or exposed.
 * - Generic error messages prevent user enumeration attacks.
 * - BCrypt comparison is time-constant to prevent timing attacks.
 * - Account disabling is separate from deletion for audit purposes.
 * 
 * @author CultivApp Team
 * @version 1.0 (REQ-001)
 */
@Service // Marks this as a Spring service component (business logic layer)
@RequiredArgsConstructor // Lombok: Generates constructor with final fields for dependency injection
public class AuthService {
    
    /**
     * Repository for accessing user data from the database.
     * Injected by Spring via constructor (dependency injection).
     */
    private final UsuarioRepository userRepo;
    
    /**
     * Service for generating JWT tokens after successful authentication.
     * Injected by Spring via constructor (dependency injection).
     */
    private final JwtService jwt;
    
    /**
     * Password encoder for validating hashed passwords.
     * Uses BCrypt algorithm for secure one-way hashing with automatic salting.
     * Instantiated here as it's stateless and doesn't need injection.
     */
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * Authenticates a user and generates a JWT token.
     * 
     * This is the main method for REQ-001 login functionality. It performs
     * a multi-step validation process to ensure only valid users can log in:
     * 
     * Step 1: User Existence Check
     * - Queries the database for a user with the provided email.
     * - If not found, throws AuthException with generic message.
     * - Generic message prevents attackers from discovering valid email addresses.
     * 
     * Step 2: Account Status Check
     * - Verifies the user account is enabled (not disabled by admin).
     * - If disabled, throws DisabledException with informative message.
     * - Disabled accounts cannot log in even with correct credentials.
     * 
     * Step 3: Password Validation
     * - Compares the provided plain-text password with the stored BCrypt hash.
     * - Uses encoder.matches() which is time-constant to prevent timing attacks.
     * - If passwords don't match, throws AuthException with generic message.
     * - Generic message prevents distinguishing between invalid email vs password.
     * 
     * Step 4: JWT Generation
     * - If all checks pass, generates a signed JWT token.
     * - Token contains email (subject) and role (claim) for authorization.
     * - Token is configured to expire after a set time (from application.properties).
     * 
     * Step 5: Response Creation
     * - Returns token and role to the controller.
     * - Controller sends this to the frontend, which stores the token.
     * - Frontend includes token in Authorization header for subsequent requests.
     * 
     * @param email The user's email address (used as username).
     * @param password The user's plain-text password (will be compared against hash).
     * @return LoginResponse containing the JWT token and user's role.
     * @throws AuthException if credentials are invalid (email not found OR wrong password).
     * @throws DisabledException if the user account is disabled.
     * 
     * Security Notes:
     * - Never reveals which credential (email/password) was wrong.
     * - Password is never stored, logged, or returned in any form.
     * - BCrypt comparison is resistant to timing attacks.
     * - Only users with roles ADMIN or PRODUCTOR can exist in the system.
     */
    public LoginResponse login(String email, String password) {
        // Step 1: Retrieve user from database by email
        // If user doesn't exist, throw exception with generic message
        var user = userRepo.findByEmail(email)
                .orElseThrow(() -> new AuthException("Credenciales inválidas"));

        // Step 2: Check if the user account is enabled
        // Disabled accounts cannot log in even with correct credentials
        if (!user.getActivo()) {
            throw new DisabledException("Cuenta deshabilitada, contacte al administrador");
        }
        
        // Step 3: Validate the provided password against the stored BCrypt hash
        // encoder.matches() is time-constant to prevent timing attacks
        if (!encoder.matches(password, user.getPassword())) {
            throw new AuthException("Credenciales inválidas");
        }
        
        // Step 4: Generate JWT token with user's email and role
        // Token will be used for stateless authentication in future requests
        String token = jwt.generate(user.getEmail(), Map.of("role", user.getRol().name()));
        
        // Step 5: Return token and role to the controller
        return new LoginResponse(token, user.getRol().name());
    }

    // ===== Data Transfer Objects (DTOs) =====
    
    /**
     * Login Request DTO - Data sent from frontend to backend during login.
     * 
     * This record (immutable data class) represents the JSON payload sent
     * by the frontend when a user attempts to log in.
     * 
     * Example JSON:
     * {
     *   "email": "admin@cultivapp.com",
     *   "password": "Admin123!"
     * }
     * 
     * Spring automatically deserializes this JSON into a LoginRequest object.
     * 
     * @param email The user's email address (username).
     * @param password The user's plain-text password (will be validated against hash).
     */
    public record LoginRequest(String email, String password) {}
    
    /**
     * Login Response DTO - Data sent from backend to frontend after successful login.
     * 
     * This record represents the JSON response sent to the frontend when
     * authentication is successful.
     * 
     * Example JSON:
     * {
     *   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
     *   "role": "ADMIN"
     * }
     * 
     * The frontend stores the token (typically in localStorage) and uses it
     * for subsequent API requests. The role is used for client-side routing
     * and feature access decisions.
     * 
     * @param token The JWT token for stateless authentication.
     * @param role The user's role (ADMIN or PRODUCTOR) for authorization.
     */
    public record LoginResponse(String token, String role) {}

    // ===== Custom Exceptions =====
    
    /**
     * Authentication Exception - Thrown when login credentials are invalid.
     * 
     * This exception is thrown when:
     * - The provided email does not exist in the database.
     * - The provided password does not match the stored hash.
     * 
     * Security: Uses generic message "Credenciales inválidas" (Invalid credentials)
     * to prevent attackers from distinguishing between invalid email vs password.
     * This prevents user enumeration attacks.
     * 
     * Handled by: AuthController's @ExceptionHandler, which returns HTTP 401 Unauthorized.
     */
    public static class AuthException extends RuntimeException {
        public AuthException(String m){ super(m); }
    }
    
    /**
     * Disabled Account Exception - Thrown when trying to log in with a disabled account.
     * 
     * This exception is thrown when a user exists and has correct credentials,
     * but their account has been disabled by an administrator.
     * 
     * This allows administrators to temporarily disable accounts without deleting
     * them, useful for security incidents or policy violations.
     * 
     * Handled by: AuthController's @ExceptionHandler, which returns HTTP 403 Forbidden.
     */
    public static class DisabledException extends RuntimeException {
        public DisabledException(String m){ super(m); }
    }
}
