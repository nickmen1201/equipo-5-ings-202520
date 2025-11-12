package com.cultivapp.cultivapp.controllers;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cultivapp.cultivapp.dto.EspecieDTO;
import com.cultivapp.cultivapp.dto.EspecieRequest;
import com.cultivapp.cultivapp.services.EspecieService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST Controller for managing Especies (REQ-005)
 * 
 * Endpoints:
 * - GET /api/especies - Get all species
 * - GET /api/especies/{id} - Get species by ID
 * - POST /api/especies - Create new species (Admin only)
 * - PUT /api/especies/{id} - Update species (Admin only)
 * - DELETE /api/especies/{id} - Delete species (Admin only)
 * 
 * Security: All endpoints are public for now (TODO: Add @PreAuthorize("hasRole('ADMIN')") for CUD operations)
 */
@RestController
@RequestMapping("/api/especies")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EspecieController {
    
    private final EspecieService especieService;
    

    /**
     * GET /api/especies
     * Get all active species
     * @return List of species DTOs
     */
    @GetMapping
    public ResponseEntity<List<EspecieDTO>> getAllEspecies() {
        List<EspecieDTO> especies = especieService.getAllEspecies();
        return ResponseEntity.ok(especies);
    }
    
    /**
     * GET /api/especies/{id}
     * Get species by ID
     * @param id Species ID
     * @return Species DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<EspecieDTO> getEspecieById(@PathVariable Integer id) {
        EspecieDTO especie = especieService.getEspecieById(id);
        return ResponseEntity.ok(especie);
    }
    
    /**
     * POST /api/especies
     * Create new species (Admin only)
     * @param request Species data
     * @return Created species DTO
     */
    @PostMapping
    public ResponseEntity<EspecieDTO> createEspecie(@Valid @RequestBody EspecieRequest request) {
        EspecieDTO created = especieService.createEspecie(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    /**
     * PUT /api/especies/{id}
     * Update existing species (Admin only)
     * @param id Species ID
     * @param request Updated species data
     * @return Updated species DTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<EspecieDTO> updateEspecie(
            @PathVariable Integer id,
            @Valid @RequestBody EspecieRequest request) {
        EspecieDTO updated = especieService.updateEspecie(id, request);
        return ResponseEntity.ok(updated);
    }
    
    /**
     * DELETE /api/especies/{id}
     * Delete species (Admin only)
     * @param id Species ID
     * @return No content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEspecie(@PathVariable Integer id) {
        especieService.deleteEspecie(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Exception handler for species not found
     * @param ex Exception
     * @return Error message with 404 status
     */
    @ExceptionHandler(EspecieService.EspecieNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleEspecieNotFound(EspecieService.EspecieNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorMessage(ex.getMessage()));
    }
    
    /**
     * Exception handler for species with associated crops
     * @param ex Exception
     * @return Error message with 409 (Conflict) status
     */
    @ExceptionHandler(EspecieService.EspecieHasCultivosException.class)
    public ResponseEntity<ErrorMessage> handleEspecieHasCultivos(EspecieService.EspecieHasCultivosException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ErrorMessage(ex.getMessage()));
    }
    
    /**
     * Error message record
     */
    record ErrorMessage(String message) {}
}

