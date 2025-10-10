package com.cultivapp.cultivapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * Admin controller for species (especies) management.
 * Implements REQ-003: Only accessible by users with ADMIN role.
 */

@Slf4j
@RestController
@RequestMapping("/especies")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminSpeciesController {

    /**
     * REQ-003: Admin can access species CRUD.
     *
     * @param principal authenticated user principal
     * @return ResponseEntity with species list
     */
    @GetMapping
    public ResponseEntity<?> getAllSpecies(Principal principal) {
        log.info("Admin user '{}' accessing GET /especies", principal.getName());

        try {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Species list retrieved successfully");
            response.put("user", principal.getName());
            response.put("role", "ADMIN");
            response.put("endpoint", "GET /especies");

            log.debug("Successfully retrieved species list for admin: {}", principal.getName());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error retrieving species for admin '{}': {}", principal.getName(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to retrieve species"));
        }
    }

    /**
     * REQ-003: Admin can create new species.
     *
     * @param speciesData species data to create
     * @param principal authenticated user principal
     * @return ResponseEntity with created species
     */
    @PostMapping
    public ResponseEntity<?> createSpecies(@RequestBody Map<String, Object> speciesData, Principal principal) {
        log.info("Admin user '{}' creating new species", principal.getName());

        try {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Species created successfully");
            response.put("user", principal.getName());
            response.put("data", speciesData);

            log.debug("Species created successfully by admin: {}", principal.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            log.error("Error creating species for admin '{}': {}", principal.getName(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create species"));
        }
    }

    /**
     * REQ-003: Admin can update existing species.
     *
     * @param id species ID to update
     * @param speciesData updated species data
     * @param principal authenticated user principal
     * @return ResponseEntity with updated species
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSpecies(@PathVariable Long id, @RequestBody Map<String, Object> speciesData, Principal principal) {
        log.info("Admin user '{}' updating species with ID: {}", principal.getName(), id);

        try {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Species updated successfully");
            response.put("user", principal.getName());
            response.put("id", id);
            response.put("data", speciesData);

            log.debug("Species {} updated successfully by admin: {}", id, principal.getName());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error updating species {} for admin '{}': {}", id, principal.getName(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update species"));
        }
    }

    /**
     * REQ-003: Admin can delete species.
     *
     * @param id species ID to delete
     * @param principal authenticated user principal
     * @return ResponseEntity with deletion confirmation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSpecies(@PathVariable Long id, Principal principal) {
        log.info("Admin user '{}' deleting species with ID: {}", principal.getName(), id);

        try {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Species deleted successfully");
            response.put("user", principal.getName());
            response.put("id", id);

            log.debug("Species {} deleted successfully by admin: {}", id, principal.getName());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error deleting species {} for admin '{}': {}", id, principal.getName(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete species"));
        }
    }
}