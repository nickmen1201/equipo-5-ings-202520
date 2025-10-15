package com.cultivapp.cultivapp.service;

import com.cultivapp.cultivapp.dto.EspecieDTO;
import com.cultivapp.cultivapp.dto.EspecieRequest;
import com.cultivapp.cultivapp.model.Especie;
import com.cultivapp.cultivapp.repository.CultivoRepository;
import com.cultivapp.cultivapp.repository.EspecieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing Especies (REQ-005)
 * Handles CRUD operations and business logic for species catalog
 */
@Service
@RequiredArgsConstructor
public class EspecieService {
    
    private final EspecieRepository especieRepository;
    private final CultivoRepository cultivoRepository;
    
    /**
     * Get all active species (excludes soft-deleted records)
     * 
     * FIX: Changed from findAll() to findByActivoTrue()
     * WHY: When species are deleted, they are marked as inactive (activo = false)
     *      but the old code returned ALL species including deleted ones.
     *      This caused deleted species to still appear in the frontend.
     * NOW: Only returns species where activo = true (visible in catalog)
     * 
     * @return List of active species as DTOs
     */
    @Transactional(readOnly = true)
    public List<EspecieDTO> getAllEspecies() {
        // Use findByActivoTrue() instead of findAll() to exclude deleted species
        return especieRepository.findByActivoTrue().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Get species by ID
     * @param id Species ID
     * @return Species DTO
     * @throws EspecieNotFoundException if species not found
     */
    @Transactional(readOnly = true)
    public EspecieDTO getEspecieById(Integer id) {
        Especie especie = especieRepository.findById(id)
            .orElseThrow(() -> new EspecieNotFoundException("Especie no encontrada con ID: " + id));
        return toDTO(especie);
    }
    
    /**
     * Create new species
     * 
     * PERSISTENCE: @Transactional ensures this saves to database
     * HOW IT WORKS:
     * 1. Build new Especie object from request data
     * 2. Set activo = true (species is active by default)
     * 3. Call especieRepository.save() - writes to database
     * 4. @Transactional commits the transaction (data is persisted)
     * 5. Return saved entity as DTO to confirm creation
     * 
     * @param request Species data from frontend
     * @return Created species DTO with generated ID
     */
    @Transactional
    public EspecieDTO createEspecie(EspecieRequest request) {
        // Build new species entity with all fields from request
        Especie especie = Especie.builder()
            .nombre(request.getNombre())
            .nombreCientifico(request.getNombreCientifico())
            .descripcion(request.getDescripcion())
            .diasFertilizacion(request.getDiasFertilizacion())
            .imagenUrl(request.getImagenUrl())
            .cicloDias(request.getCicloDias())
            .diasGerminacion(request.getDiasGerminacion())
            .diasFloracion(request.getDiasFloracion())
            .diasCosecha(request.getDiasCosecha())
            .aguaSemanalMm(request.getAguaSemanalMm())
            .activo(true)  // New species are active by default
            .build();
        
        // Save to database - this persists the new record
        Especie saved = especieRepository.save(especie);
        return toDTO(saved);
    }
    
    /**
     * Update existing species
     * 
     * PERSISTENCE: @Transactional ensures changes save to database
     * HOW IT WORKS:
     * 1. Find existing species by ID (throws error if not found)
     * 2. Update all fields with new values from request
     * 3. Call especieRepository.save() - updates database record
     * 4. @Transactional commits the transaction (changes are persisted)
     * 5. Return updated entity as DTO to confirm update
     * 
     * @param id Species ID to update
     * @param request Updated species data from frontend
     * @return Updated species DTO
     * @throws EspecieNotFoundException if species not found
     */
    @Transactional
    public EspecieDTO updateEspecie(Integer id, EspecieRequest request) {
        // Find existing species (throws exception if deleted or doesn't exist)
        Especie especie = especieRepository.findById(id)
            .orElseThrow(() -> new EspecieNotFoundException("Especie no encontrada con ID: " + id));
        
        // Update all fields with new values
        especie.setNombre(request.getNombre());
        especie.setNombreCientifico(request.getNombreCientifico());
        especie.setDescripcion(request.getDescripcion());
        especie.setDiasFertilizacion(request.getDiasFertilizacion());
        especie.setImagenUrl(request.getImagenUrl());
        especie.setCicloDias(request.getCicloDias());
        especie.setDiasGerminacion(request.getDiasGerminacion());
        especie.setDiasFloracion(request.getDiasFloracion());
        especie.setDiasCosecha(request.getDiasCosecha());
        especie.setAguaSemanalMm(request.getAguaSemanalMm());
        
        // Save changes to database - this persists the updates
        Especie updated = especieRepository.save(especie);
        return toDTO(updated);
    }
    
    /**
     * Delete species (soft delete by marking as inactive)
     * 
     * SOFT DELETE EXPLAINED:
     * - Does NOT remove the record from database (no hard delete)
     * - Instead, sets activo = false to hide it from catalog
     * - WHY: Preserves data integrity and historical records
     * - FIX: Updated getAllEspecies() to filter out inactive species
     * 
     * PERSISTENCE: @Transactional ensures the update saves to database
     * HOW IT WORKS:
     * 1. Find species by ID (throws error if not found)
     * 2. Check if species has crops using it (prevents deletion)
     * 3. Set activo = false (marks as deleted)
     * 4. Save to database - this persists the "deleted" status
     * 5. getAllEspecies() won't return this species anymore (filtered out)
     * 
     * @param id Species ID to delete
     * @throws EspecieNotFoundException if species not found
     * @throws EspecieHasCultivosException if species has associated crops
     */
    @Transactional
    public void deleteEspecie(Integer id) {
        // Find existing species (throws exception if doesn't exist)
        Especie especie = especieRepository.findById(id)
            .orElseThrow(() -> new EspecieNotFoundException("Especie no encontrada con ID: " + id));
        
        // Safety check: prevent deleting species that are in use
        // REQ-005 requirement: can't delete species with associated crops
        if (cultivoRepository.existsByEspecieId(id)) {
            throw new EspecieHasCultivosException("No se puede eliminar: tiene cultivos asociados");
        }
        
        // Soft delete: mark as inactive (not visible in catalog anymore)
        especie.setActivo(false);
        // Save the change to database - this persists the deletion
        especieRepository.save(especie);
        // Note: Record stays in database but getAllEspecies() filters it out
    }
    
    /**
     * Convert Especie entity to DTO
     * @param especie Entity
     * @return DTO
     */
    private EspecieDTO toDTO(Especie especie) {
        return EspecieDTO.builder()
            .id(especie.getId())
            .nombre(especie.getNombre())
            .nombreCientifico(especie.getNombreCientifico())
            .descripcion(especie.getDescripcion())
            .diasFertilizacion(especie.getDiasFertilizacion())
            .imagenUrl(especie.getImagenUrl())
            .cicloDias(especie.getCicloDias())
            .diasGerminacion(especie.getDiasGerminacion())
            .diasFloracion(especie.getDiasFloracion())
            .diasCosecha(especie.getDiasCosecha())
            .aguaSemanalMm(especie.getAguaSemanalMm())
            .activo(especie.getActivo())
            .build();
    }
    
    // Custom exceptions
    public static class EspecieNotFoundException extends RuntimeException {
        public EspecieNotFoundException(String message) {
            super(message);
        }
    }
    
    public static class EspecieHasCultivosException extends RuntimeException {
        public EspecieHasCultivosException(String message) {
            super(message);
        }
    }
}
