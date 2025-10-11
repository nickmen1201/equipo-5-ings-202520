package com.cultivapp.cultivapp.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * User Repository - Data Access Layer for User Entity (REQ-001: Login Authentication)
 * 
 * This interface defines the database operations for the User entity. It extends
 * Spring Data JPA's JpaRepository, which provides standard CRUD operations 
 * (create, read, update, delete) without requiring implementation code.
 * 
 * Repository Layer Responsibilities:
 * - Abstracts database access from the service layer (AuthService).
 * - Provides type-safe methods for querying users.
 * - Returns Optional to handle cases where a user might not exist.
 * 
 * Spring Data JPA automatically implements this interface at runtime, generating
 * the necessary SQL queries based on method names.
 * 
 * REQ-001 Usage:
 * - During login, AuthService calls findByEmail() to retrieve the user attempting to log in.
 * - If the user is not found, an empty Optional is returned, and login is rejected.
 * 
 * @author CultivApp Team
 * @version 1.0 (REQ-001)
 */
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Finds a user by their email address (used as username for login).
     * 
     * This method is used in REQ-001 to retrieve the user during the login process.
     * Spring Data JPA automatically generates the SQL query:
     * SELECT * FROM users WHERE email = ?
     * 
     * @param email The email address to search for (case-sensitive).
     * @return Optional<User> containing the user if found, or empty Optional if not found.
     *         Using Optional prevents NullPointerException and makes the API clearer.
     * 
     * Security Note: This method does not expose whether a user exists to prevent
     * user enumeration attacks. The service layer handles this by returning a generic
     * "invalid credentials" message regardless of whether the email or password is wrong.
     */
    Optional<User> findByEmail(String email);
}
