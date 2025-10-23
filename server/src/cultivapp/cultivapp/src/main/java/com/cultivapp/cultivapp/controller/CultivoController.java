package com.cultivapp.cultivapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cultivapp.cultivapp.dto.CultivoDTO;
import com.cultivapp.cultivapp.dto.CultivoDetailDTO;
import com.cultivapp.cultivapp.model.Cultivo;
import com.cultivapp.cultivapp.service.CultivoService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/cultivos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CultivoController {
    private final CultivoService cultivoService;

    @GetMapping
    public ResponseEntity<List<CultivoDTO>> getAllCultivos() {
        return ResponseEntity.ok(cultivoService.getAllCultivos());
    }
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CultivoDTO>> getCultivosByUsuarioId(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(cultivoService.getCultivosByUsuarioId(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CultivoDetailDTO> getCultivoById(@PathVariable Integer id) {
        return ResponseEntity.ok(cultivoService.getCultivoDetailById(id));
    }

    @PostMapping
    public ResponseEntity<Cultivo> createCultivo(@RequestBody Cultivo cultivo) {
        Cultivo created = cultivoService.createCultivo(cultivo);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cultivo> updateCultivo(@PathVariable Integer id, @RequestBody Cultivo cultivo) {
        Cultivo updated = cultivoService.updateCultivo(id, cultivo);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCultivo(@PathVariable Integer id) {
        cultivoService.deleteCultivo(id);
        return ResponseEntity.noContent().build();    
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Cultivo> toggleEstado(@PathVariable Integer id) {
        Cultivo updated = cultivoService.toggleEstado(id);
        return ResponseEntity.ok(updated);
    }
  
    @ExceptionHandler(CultivoService.CultivoNotFoundException.class)
    public ResponseEntity<String> handleCultivoNotFound(CultivoService.CultivoNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(CultivoService.ArchivedCropException.class)
    public ResponseEntity<String> handleArchivedCrop(CultivoService.ArchivedCropException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }
}
