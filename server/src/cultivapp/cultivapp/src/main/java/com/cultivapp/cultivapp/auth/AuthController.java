package com.cultivapp.cultivapp.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication Controller (REQ-001: Login)
 * 
 * Handles login HTTP requests:
 * - POST /api/auth/login → validates credentials, returns JWT token + role
 * - Returns 200 OK (success), 401 (invalid credentials), 403 (disabled account)
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService auth;

    /**
     * POST /api/auth/login
     * Request: { "email": "user@example.com", "password": "password123" }
     * Response: { "token": "eyJhbGci...", "role": "ADMIN" }
     */
    @PostMapping("/login")
    public ResponseEntity<AuthService.LoginResponse> login(@RequestBody AuthService.LoginRequest body){
        var res = auth.login(body.email(), body.password());
        return ResponseEntity.ok(res);
    }

    @ExceptionHandler(AuthService.AuthException.class)
    public ResponseEntity<?> handleBadCreds(AuthService.AuthException ex){
        return ResponseEntity.status(401).body(new ErrorMsg(ex.getMessage()));
    }

    @ExceptionHandler(AuthService.DisabledException.class)
    public ResponseEntity<?> handleDisabled(AuthService.DisabledException ex){
        return ResponseEntity.status(403).body(new ErrorMsg(ex.getMessage()));
    }

    record ErrorMsg(String message){}
}
