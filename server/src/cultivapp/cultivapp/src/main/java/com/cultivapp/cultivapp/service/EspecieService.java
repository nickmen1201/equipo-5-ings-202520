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
     * Get all active species
     * @return List of active species as DTOs
     */
    @Transactional(readOnly = true)
    public List<EspecieDTO> getAllEspecies() {
        return especieRepository.findAll().stream()
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
     * @param request Species data
     * @return Created species DTO
     */
    @Transactional
    public EspecieDTO createEspecie(EspecieRequest request) {
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
            .activo(true)
            .build();
        
        Especie saved = especieRepository.save(especie);
        return toDTO(saved);
    }
    
    /**
     * Update existing species
     * @param id Species ID
     * @param request Updated species data
     * @return Updated species DTO
     * @throws EspecieNotFoundException if species not found
     */
    @Transactional
    public EspecieDTO updateEspecie(Integer id, EspecieRequest request) {
        Especie especie = especieRepository.findById(id)
            .orElseThrow(() -> new EspecieNotFoundException("Especie no encontrada con ID: " + id));
        
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
        
        Especie updated = especieRepository.save(especie);
        return toDTO(updated);
    }
    
    /**
     * Delete species (soft delete by marking as inactive)
     * @param id Species ID
     * @throws EspecieNotFoundException if species not found
     * @throws EspecieHasCultivosException if species has associated crops
     */
    @Transactional
    public void deleteEspecie(Integer id) {
        Especie especie = especieRepository.findById(id)
            .orElseThrow(() -> new EspecieNotFoundException("Especie no encontrada con ID: " + id));
        
        // Check if species has associated crops (REQ-005 requirement)
        if (cultivoRepository.existsByEspecieId(id)) {
            throw new EspecieHasCultivosException("No se puede eliminar: tiene cultivos asociados");
        }
        
        // Soft delete: mark as inactive instead of hard delete
        especie.setActivo(false);
        especieRepository.save(especie);
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
