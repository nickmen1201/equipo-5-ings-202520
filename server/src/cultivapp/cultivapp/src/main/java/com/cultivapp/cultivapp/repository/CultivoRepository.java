package com.cultivapp.cultivapp.repository;

import com.cultivapp.cultivapp.model.Cultivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// Repository for Cultivo entity: provides database access methods
@Repository
public interface CultivoRepository extends JpaRepository<Cultivo, Integer> {
    
    // Find all crops for a specific user
    List<Cultivo> findByUsuarioId(Integer usuarioId);
}
