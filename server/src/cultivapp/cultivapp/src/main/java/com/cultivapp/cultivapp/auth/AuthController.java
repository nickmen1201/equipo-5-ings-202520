package com.cultivapp.cultivapp.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication Controller - REST API Layer for Login (REQ-001: Login Authentication)
 * 
 * This controller handles HTTP requests for authentication operations. It acts as the
 * entry point for the frontend to communicate with the backend authentication system.
 * 
 * Controller Layer Responsibilities in Layered Architecture:
 * - Receives HTTP requests from the frontend (UI layer).
 * - Validates request format and extracts data from JSON payloads.
 * - Delegates business logic to the AuthService (service layer).
 * - Converts service responses into HTTP responses with appropriate status codes.
 * - Handles exceptions and maps them to appropriate HTTP error responses.
 * 
 * REQ-001 Login Flow (UI → Controller → Service → Repository → DB):
 * 1. Frontend sends POST request to /api/auth/login with email/password JSON.
 * 2. Controller receives request and extracts LoginRequest DTO.
 * 3. Controller calls AuthService.login() with credentials.
 * 4. Service validates credentials and generates JWT (interacts with Repository/DB).
 * 5. Service returns LoginResponse (token + role) to Controller.
 * 6. Controller wraps response in HTTP 200 OK and sends to frontend.
 * 7. If error occurs, exception handler converts it to appropriate HTTP error.
 * 
 * REST API Endpoint:
 * - POST /api/auth/login - Authenticates user and returns JWT token.
 * 
 * HTTP Status Codes:
 * - 200 OK: Login successful, returns JWT and role.
 * - 401 Unauthorized: Invalid credentials (email not found or wrong password).
 * - 403 Forbidden: Account is disabled.
 * - 500 Internal Server Error: Unexpected server error.
 * 
 * Security Features:
 * - CORS enabled for development (configured for Vite dev server).
 * - Generic error messages to prevent information disclosure.
 * - No sensitive data logged or exposed in responses.
 * 
 * @author CultivApp Team
 * @version 1.0 (REQ-001)
 */
@RestController // Marks this as a REST controller (auto-serializes to JSON)
@RequestMapping("/api/auth") // Base path for all endpoints in this controller
@RequiredArgsConstructor // Lombok: Generates constructor for dependency injection
@CrossOrigin(origins = "*") // Allows requests from any origin (for Vite dev server on different port)
public class AuthController {

    /**
     * Authentication service for handling login business logic.
     * Injected by Spring via constructor (dependency injection).
     * Controller remains thin - all business logic is in the service layer.
     */
    private final AuthService auth;

    /**
     * Login endpoint - Authenticates user and returns JWT token.
     * 
     * This endpoint implements REQ-001 by providing a REST API for user login.
     * It receives credentials from the frontend, validates them via AuthService,
     * and returns a JWT token for stateless authentication.
     * 
     * HTTP Request:
     * - Method: POST
     * - Path: /api/auth/login
     * - Content-Type: application/json
     * - Body: { "email": "user@example.com", "password": "password123" }
     * 
     * HTTP Response (Success - 200 OK):
     * - Body: { "token": "eyJhbGci...", "role": "ADMIN" }
     * - The token should be stored by the frontend (typically in localStorage).
     * - The role is used for client-side routing and feature access.
     * 
     * HTTP Response (Failure - 401 Unauthorized):
     * - Body: { "message": "Credenciales inválidas" }
     * - Returned when email doesn't exist OR password is wrong.
     * - Generic message prevents user enumeration attacks.
     * 
     * HTTP Response (Failure - 403 Forbidden):
     * - Body: { "message": "Cuenta deshabilitada, contacte al administrador" }
     * - Returned when credentials are correct but account is disabled.
     * 
     * Controller Pattern (Thin Controller):
     * - Controller only handles HTTP concerns (request/response mapping).
     * - All business logic (validation, password checking, JWT generation) is in AuthService.
     * - This follows the Single Responsibility Principle and makes testing easier.
     * 
     * @param body LoginRequest DTO containing email and password.
     *             Spring automatically deserializes JSON to this object.
     * @return ResponseEntity with LoginResponse (token + role) and HTTP 200 status.
     * @throws AuthService.AuthException if credentials are invalid (handled by exception handler).
     * @throws AuthService.DisabledException if account is disabled (handled by exception handler).
     */
    @PostMapping("/login") // Maps to POST /api/auth/login
    public ResponseEntity<AuthService.LoginResponse> login(@RequestBody AuthService.LoginRequest body){
        // Delegate authentication logic to service layer
        var res = auth.login(body.email(), body.password());
        
        // Return successful response with JWT token and user role
        return ResponseEntity.ok(res); // HTTP 200 OK
    }

    /**
     * Exception Handler - Converts AuthException to HTTP 401 Unauthorized response.
     * 
     * This method is automatically called by Spring when AuthService throws an
     * AuthException (invalid credentials). It converts the exception into an
     * appropriate HTTP response.
     * 
     * Scenarios that trigger this handler:
     * - Email address not found in database.
     * - Password doesn't match the stored hash.
     * 
     * HTTP 401 Unauthorized: Standard status code for authentication failures.
     * The frontend should display an error message when receiving this status.
     * 
     * Security: Returns generic message to prevent distinguishing between
     * invalid email vs invalid password (prevents user enumeration).
     * 
     * @param ex The AuthException thrown by AuthService.
     * @return ResponseEntity with error message and HTTP 401 status.
     */
    @ExceptionHandler(AuthService.AuthException.class)
    public ResponseEntity<?> handleBadCreds(AuthService.AuthException ex){
        // Return generic error message with 401 Unauthorized status
        return ResponseEntity.status(401).body(new ErrorMsg(ex.getMessage()));
    }

    /**
     * Exception Handler - Converts DisabledException to HTTP 403 Forbidden response.
     * 
     * This method is automatically called by Spring when AuthService throws a
     * DisabledException (account disabled by admin). It converts the exception
     * into an appropriate HTTP response.
     * 
     * Scenarios that trigger this handler:
     * - User exists with correct credentials, but account is disabled.
     * 
     * HTTP 403 Forbidden: Standard status code for authorization failures.
     * Different from 401 (authentication failure) - credentials are valid
     * but the user is not authorized to access the system.
     * 
     * The frontend should display a message instructing the user to contact
     * an administrator to re-enable their account.
     * 
     * @param ex The DisabledException thrown by AuthService.
     * @return ResponseEntity with error message and HTTP 403 status.
     */
    @ExceptionHandler(AuthService.DisabledException.class)
    public ResponseEntity<?> handleDisabled(AuthService.DisabledException ex){
        // Return informative message with 403 Forbidden status
        return ResponseEntity.status(403).body(new ErrorMsg(ex.getMessage()));
    }

    /**
     * Error Message DTO - Simple record for error responses.
     * 
     * Used to wrap error messages in a consistent JSON format.
     * 
     * Example JSON:
     * {
     *   "message": "Credenciales inválidas"
     * }
     * 
     * @param message The error message to send to the frontend.
     */
    record ErrorMsg(String message){}
}
