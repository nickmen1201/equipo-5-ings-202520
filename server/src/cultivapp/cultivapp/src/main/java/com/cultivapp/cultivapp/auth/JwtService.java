package com.cultivapp.cultivapp.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

/**
 * JWT Service - Token Generation and Management (REQ-001: Login Authentication)
 * 
 * This service is responsible for generating JSON Web Tokens (JWT) after successful
 * user authentication. JWTs are used for stateless authentication, allowing the client
 * to prove their identity on subsequent requests without storing session data on the server.
 * 
 * JWT Structure:
 * - Header: Contains the token type (JWT) and signing algorithm (HS256).
 * - Payload: Contains claims (user data) such as email (subject) and role.
 * - Signature: Ensures the token hasn't been tampered with, created using a secret key.
 * 
 * Security Features:
 * - Tokens are signed with HMAC-SHA256 algorithm to prevent tampering.
 * - Tokens have a configurable expiration time to limit their validity.
 * - The secret key is loaded from application.properties for easy configuration.
 * 
 * REQ-001 Usage:
 * - After successful login, AuthService calls generate() to create a JWT.
 * - The JWT contains the user's email (subject) and role (claim).
 * - The client stores this token and sends it with future API requests.
 * 
 * @author CultivApp Team
 * @version 1.0 (REQ-001)
 */
@Service // Marks this as a Spring service component
public class JwtService {

    /**
     * Secret key used to sign JWT tokens.
     * Generated from the secret string configured in application.properties.
     * Must be at least 256 bits (32 bytes) for HS256 algorithm security.
     */
    private final SecretKey key;
    
    /**
     * Token expiration time in minutes.
     * After this time, the token will no longer be valid and the user must log in again.
     * Configured in application.properties (default: 120 minutes = 2 hours).
     */
    private final long expMinutes;

    /**
     * Constructor - Initializes JWT configuration from application.properties.
     * 
     * Spring automatically injects the values from application.properties:
     * - security.jwt.secret: The secret string used to sign tokens.
     * - security.jwt.exp-minutes: How long tokens remain valid.
     * 
     * The secret is converted into a SecretKey object for use with JJWT library.
     * 
     * @param secret The secret string from application.properties.
     * @param expMinutes Token expiration time in minutes from application.properties.
     */
    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.exp-minutes}") long expMinutes
    ) {
        // Convert the secret string into a proper HMAC-SHA256 key
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expMinutes = expMinutes;
    }

    /**
     * Generates a signed JWT token for an authenticated user.
     * 
     * This method is called by AuthService after successfully validating login credentials.
     * The generated token contains:
     * - Subject: The user's email (used to identify who owns the token).
     * - Claims: Additional data like the user's role (ADMIN or PRODUCTOR).
     * - Issued At: Timestamp when the token was created.
     * - Expiration: Timestamp when the token will expire.
     * - Signature: HMAC-SHA256 signature to prevent tampering.
     * 
     * Token Flow in REQ-001:
     * 1. User logs in with valid credentials.
     * 2. AuthService calls this method with the user's email and role.
     * 3. A JWT is generated and returned to the client.
     * 4. Client stores the JWT (typically in localStorage).
     * 5. Client includes the JWT in the Authorization header for future API calls.
     * 
     * @param subject The subject of the token (typically the user's email).
     *                This identifies who the token belongs to.
     * @param claims Additional data to include in the token (e.g., {"role": "ADMIN"}).
     *               These claims can be used for authorization decisions.
     * @return A signed JWT token as a String, ready to be sent to the client.
     * 
     * Security Note: The token is signed but not encrypted. Don't include sensitive
     * data in claims as they can be decoded (but not modified without the secret key).
     */
    public String generate(String subject, Map<String, Object> claims) {
        // Get current time for "issued at" timestamp
        Instant now = Instant.now();
        
        // Calculate expiration time by adding configured minutes
        Instant exp = now.plusSeconds(expMinutes * 60);
        
        // Build and sign the JWT token
        return Jwts.builder()
                .setSubject(subject)              // Who the token is for (email)
                .addClaims(claims)                 // Additional data (role, etc.)
                .setIssuedAt(Date.from(now))      // When the token was created
                .setExpiration(Date.from(exp))     // When the token expires
                .signWith(key, SignatureAlgorithm.HS256) // Sign with HMAC-SHA256
                .compact();                        // Convert to compact string format
    }
}