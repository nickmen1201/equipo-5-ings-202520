package com.cultivapp.cultivapp.controllers;


import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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

/**
 * Admin controller for species (especies) management.
 * Implements REQ-003: Only accessible by users with ADMIN role.
 */

@RestController
@RequestMapping("/api/admin/especies")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "*")
public class AdminSpeciesController {
    
    private static final Logger log = LoggerFactory.getLogger(AdminSpeciesController.class);
    private final EspecieService especieService;
    
    public AdminSpeciesController(EspecieService especieService) {
        this.especieService = especieService;
    }

    /**
     * REQ-003: Admin can access species CRUD - GET all species
     *
     * @param principal authenticated user principal
     * @return ResponseEntity with species list
     */
    @GetMapping
    public ResponseEntity<List<EspecieDTO>> getAllSpecies(Principal principal) {
        log.info("Admin user '{}' accessing GET /api/admin/especies", principal.getName());

        try {
            List<EspecieDTO> especies = especieService.getAllEspecies();
            log.debug("Successfully retrieved {} species for admin: {}", especies.size(), principal.getName());
            return ResponseEntity.ok(especies);

        } catch (Exception e) {
            log.error("Error retrieving species for admin '{}': {}", principal.getName(), e.getMessage(), e);
            throw e;
        }
    }

    /**
     * REQ-003: Admin can create new species
     *
     * @param request species data to create
     * @param principal authenticated user principal
     * @return ResponseEntity with created species
     */
    @PostMapping
    public ResponseEntity<EspecieDTO> createSpecies(@Valid @RequestBody EspecieRequest request, Principal principal) {
        log.info("Admin user '{}' creating new species: {}", principal.getName(), request.getNombre());

        try {
            EspecieDTO created = especieService.createEspecie(request);
            log.debug("Species created successfully by admin: {} - Species ID: {}", principal.getName(), created.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(created);

        } catch (IllegalArgumentException e) {
            log.warn("Invalid data for species creation by admin '{}': {}", principal.getName(), e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error creating species for admin '{}': {}", principal.getName(), e.getMessage(), e);
            throw e;
        }
    }

    /**
     * REQ-003: Admin can update existing species
     *
     * @param id species ID to update
     * @param request updated species data
     * @param principal authenticated user principal
     * @return ResponseEntity with updated species
     */
    @PutMapping("/{id}")
    public ResponseEntity<EspecieDTO> updateSpecies(@PathVariable Integer id, @Valid @RequestBody EspecieRequest request, Principal principal) {
        log.info("Admin user '{}' updating species with ID: {}", principal.getName(), id);

        try {
            EspecieDTO updated = especieService.updateEspecie(id, request);
            log.debug("Species {} updated successfully by admin: {}", id, principal.getName());
            return ResponseEntity.ok(updated);

        } catch (IllegalArgumentException e) {
            log.warn("Invalid data for species update by admin '{}': {}", principal.getName(), e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error updating species {} for admin '{}': {}", id, principal.getName(), e.getMessage(), e);
            throw e;
        }
    }

    /**
     * REQ-003: Admin can delete species
     *
     * @param id species ID to delete
     * @param principal authenticated user principal
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecies(@PathVariable Integer id, Principal principal) {
        log.info("Admin user '{}' deleting species with ID: {}", principal.getName(), id);

        try {
            especieService.deleteEspecie(id);
            log.debug("Species {} deleted successfully by admin: {}", id, principal.getName());
            return ResponseEntity.noContent().build();

        } catch (IllegalStateException e) {
            log.warn("Cannot delete species {} - has associated crops: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error deleting species {} for admin '{}': {}", id, principal.getName(), e.getMessage(), e);
            throw e;
        }
    }
}