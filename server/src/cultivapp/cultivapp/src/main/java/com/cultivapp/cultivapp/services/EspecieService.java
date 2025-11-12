package com.cultivapp.cultivapp.services;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cultivapp.cultivapp.dto.EspecieDTO;
import com.cultivapp.cultivapp.dto.EspecieRequest;
import com.cultivapp.cultivapp.models.Especie;
import com.cultivapp.cultivapp.repositories.CultivoRepository;
import com.cultivapp.cultivapp.repositories.EspecieRepository;

/**
 * Service for managing Especies (REQ-005)
 * Handles CRUD operations and business logic for species catalog
 */
@Service
public class EspecieService {

    private final NotificacionService notificacionService;
    
    private final EspecieRepository especieRepository;
    private final CultivoRepository cultivoRepository;
    private final EtapaService etapaService;
    
    public EspecieService(EspecieRepository especieRepository, CultivoRepository cultivoRepository, EtapaService etapaService, NotificacionService notificacionService) {
        this.especieRepository = especieRepository;
        this.cultivoRepository = cultivoRepository;
        this.etapaService = etapaService;
        this.notificacionService = notificacionService;
    }
    
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
     * Create new species or reactivate if exists as inactive
     * 
     * LOGIC:
     * 1. Check if species with same name exists (case-insensitive)
     * 2. If exists and inactive → reactivate with new data
     * 3. If exists and active → throw error (duplicate)
     * 4. If not exists → create new species
     * 
     * @param request Species data from frontend
     * @return Created or reactivated species DTO
     */
    @Transactional
    public EspecieDTO createEspecie(EspecieRequest request) {
        // Check if species with this name already exists (case-insensitive)
        // Uses findFirst to get most recent if duplicates exist
        Optional<Especie> existingEspecie = especieRepository.findFirstByNombreIgnoreCaseOrderByIdDesc(request.getNombre());
        
        if (existingEspecie.isPresent()) {
            Especie especie = existingEspecie.get();
            
            // If species exists and is active → duplicate error
            if (Boolean.TRUE.equals(especie.getActivo())) {
                throw new IllegalArgumentException("Ya existe una especie activa con el nombre: " + request.getNombre());
            }
            
            // If species exists but inactive → reactivate with new data
            especie.setNombre(request.getNombre());
            especie.setNombreCientifico(request.getNombreCientifico());
            especie.setDescripcion(request.getDescripcion());
            especie.setImagenUrl(request.getImagenUrl());
            especie.setAguaSemanalMm(request.getAguaSemanalMm());
            especie.setActivo(true);  // Reactivate species
            
            Especie saved = especieRepository.save(especie);
            return toDTO(saved);
        }
        
        // Species doesn't exist → create new one
        Especie especie = Especie.builder()
            .nombre(request.getNombre())
            .nombreCientifico(request.getNombreCientifico())
            .descripcion(request.getDescripcion())
            .imagenUrl(request.getImagenUrl())
            .aguaSemanalMm(request.getAguaSemanalMm())
            .activo(true)
            .build();
        
        Especie saved = especieRepository.save(especie);

        String mensaje = "Nueva Especie Añadida: " + saved.getNombre();

        notificacionService.createNotificacionParaTodos(mensaje);

        // Crear las etapas asociadas
        if (request.getEtapas() != null) {
            for (EtapaRequest etapaRequest : request.getEtapas()) {
                etapaRequest.setEspecieId(saved.getId());
                etapaService.createEtapa(etapaRequest);
            }
        }
        
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
        especie.setImagenUrl(request.getImagenUrl());
        especie.setAguaSemanalMm(request.getAguaSemanalMm());
        
        // Save changes to database - this persists the updates
        Especie updated = especieRepository.save(especie);

        // Actualizar las etapas si se proporcionaron
        if (request.getEtapas() != null) {
            // Primero eliminar todas las etapas existentes
            var etapasActuales = etapaService.getEtapasByEspecie(id);
            for (var etapaDTO : etapasActuales) {
                etapaService.deleteEtapa(etapaDTO.getId());
            }

            // Crear las nuevas etapas
            for (EtapaRequest etapaRequest : request.getEtapas()) {
                etapaRequest.setEspecieId(updated.getId());
                etapaService.createEtapa(etapaRequest);
            }
        }
        
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
        var dto = EspecieDTO.builder()
            .id(especie.getId())
            .nombre(especie.getNombre())
            .nombreCientifico(especie.getNombreCientifico())
            .descripcion(especie.getDescripcion())
            .imagenUrl(especie.getImagenUrl())
            .aguaSemanalMm(especie.getAguaSemanalMm())
            .activo(especie.getActivo())
            .build();
        
        // Agregar las etapas al DTO
        dto.setEtapas(etapaService.getEtapasByEspecie(especie.getId()));
        return dto;
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
