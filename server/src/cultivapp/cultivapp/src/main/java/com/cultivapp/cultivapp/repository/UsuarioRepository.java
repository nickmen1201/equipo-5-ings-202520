package com.cultivapp.cultivapp.repository;

import com.cultivapp.cultivapp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

// Repository for Usuario entity: provides database access methods
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    // Find user by email for login authentication
    Optional<Usuario> findByEmail(String email);
}
