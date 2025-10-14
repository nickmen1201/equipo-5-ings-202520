package com.cultivapp.cultivapp.repository;

import com.cultivapp.cultivapp.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// Repository for Alerta entity: provides database access methods
@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Integer> {
    
    // Find all alerts for a specific crop
    List<Alerta> findByCultivoId(Integer cultivoId);
}
