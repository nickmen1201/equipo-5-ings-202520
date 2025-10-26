package com.cultivapp.cultivapp.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cultivapp.cultivapp.models.Cultivo;

@Repository
public interface CultivoRepository extends JpaRepository<Cultivo, Integer> {
    
    // Find all crops for a specific user
    List<Cultivo> findByUsuarioId(Integer usuarioId);
    
    // Check if any crop exists for a specific species (REQ-005)
    boolean existsByEspecieId(Integer especieId);
}
