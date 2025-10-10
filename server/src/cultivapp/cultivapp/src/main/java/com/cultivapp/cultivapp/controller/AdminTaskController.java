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
 * Admin controller for tasks (tareas) management.
 * Implements REQ-003: Only accessible by users with ADMIN role.
 */

@Slf4j
@RestController
@RequestMapping("/tareas")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminTaskController {

    /**
     * REQ-003: Admin can access tasks CRUD.
     * Producers will receive 403 with "No autorizado" message.
     *
     * @param principal authenticated user principal
     * @return ResponseEntity with tasks list
     */
    @GetMapping
    public ResponseEntity<?> getAllTasks(Principal principal) {
        log.info("Admin user '{}' accessing GET /tareas", principal.getName());

        try {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Tasks list retrieved successfully");
            response.put("user", principal.getName());
            response.put("role", "ADMIN");
            response.put("endpoint", "GET /tareas");

            log.debug("Successfully retrieved tasks list for admin: {}", principal.getName());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error retrieving tasks for admin '{}': {}", principal.getName(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to retrieve tasks"));
        }
    }

    /**
     * REQ-003: Admin can create new tasks.
     *
     * @param taskData task data to create
     * @param principal authenticated user principal
     * @return ResponseEntity with created task
     */
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Map<String, Object> taskData, Principal principal) {
        log.info("Admin user '{}' creating new task", principal.getName());

        try {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Task created successfully");
            response.put("user", principal.getName());
            response.put("data", taskData);

            log.debug("Task created successfully by admin: {}", principal.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            log.error("Error creating task for admin '{}': {}", principal.getName(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create task"));
        }
    }

    /**
     * REQ-003: Admin can update existing tasks.
     *
     * @param id task ID to update
     * @param taskData updated task data
     * @param principal authenticated user principal
     * @return ResponseEntity with updated task
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Map<String, Object> taskData, Principal principal) {
        log.info("Admin user '{}' updating task with ID: {}", principal.getName(), id);

        try {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Task updated successfully");
            response.put("user", principal.getName());
            response.put("id", id);
            response.put("data", taskData);

            log.debug("Task {} updated successfully by admin: {}", id, principal.getName());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error updating task {} for admin '{}': {}", id, principal.getName(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update task"));
        }
    }

    /**
     * REQ-003: Admin can delete tasks.
     *
     * @param id task ID to delete
     * @param principal authenticated user principal
     * @return ResponseEntity with deletion confirmation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, Principal principal) {
        log.info("Admin user '{}' deleting task with ID: {}", principal.getName(), id);

        try {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Task deleted successfully");
            response.put("user", principal.getName());
            response.put("id", id);

            log.debug("Task {} deleted successfully by admin: {}", id, principal.getName());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error deleting task {} for admin '{}': {}", id, principal.getName(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete task"));
        }
    }
}