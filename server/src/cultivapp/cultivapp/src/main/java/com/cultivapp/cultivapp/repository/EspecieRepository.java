package com.cultivapp.cultivapp.repository;

import com.cultivapp.cultivapp.model.Especie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Repository for Especie entity: provides database access methods
@Repository
public interface EspecieRepository extends JpaRepository<Especie, Integer> {
    
    /**
     * Find all active species (excludes soft-deleted records)
     * This method filters species where activo = true
     * Used to show only available species in the catalog
     * 
     * @return List of active species only
     */
    List<Especie> findByActivoTrue();
    
    /**
     * Find species by name (case-insensitive)
     * Returns the most recent match if multiple exist (ordered by ID desc)
     * Used to check if species already exists (active or inactive)
     * 
     * @param nombre Species name
     * @return Optional with the most recent species matching the name
     */
    Optional<Especie> findFirstByNombreIgnoreCaseOrderByIdDesc(String nombre);
}
