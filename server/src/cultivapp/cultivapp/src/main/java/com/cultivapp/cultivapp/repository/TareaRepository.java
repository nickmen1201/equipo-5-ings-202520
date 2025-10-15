package com.cultivapp.cultivapp.repository;

import com.cultivapp.cultivapp.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// Repository for Tarea entity: provides database access methods
@Repository
public interface TareaRepository extends JpaRepository<Tarea, Integer> {
    
    // Find all tasks for a specific crop
    List<Tarea> findByCultivoId(Integer cultivoId);
}
