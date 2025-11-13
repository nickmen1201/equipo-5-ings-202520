package com.cultivapp.cultivapp.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cultivapp.cultivapp.dto.EtapaDTO;
import com.cultivapp.cultivapp.dto.EtapaRequest;
import com.cultivapp.cultivapp.models.enums.TipoEtapa;
import com.cultivapp.cultivapp.services.EtapaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/etapas")
@RequiredArgsConstructor
public class EtapaController {

    private final EtapaService etapaService;

    @PostMapping
    public ResponseEntity<EtapaDTO> createEtapa(@Valid @RequestBody EtapaRequest request) {
        return ResponseEntity.ok(etapaService.createEtapa(request));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<EtapaDTO>> createEtapas(@Valid @RequestBody List<EtapaRequest> requests) {
        return ResponseEntity.ok(etapaService.createEtapas(requests));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EtapaDTO> getEtapa(@PathVariable Integer id) {
        return ResponseEntity.ok(etapaService.getEtapaById(id));
    }

    @GetMapping
    public ResponseEntity<List<EtapaDTO>> getAllEtapas() {
        return ResponseEntity.ok(etapaService.getAllEtapas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EtapaDTO> updateEtapa(@PathVariable Integer id, @Valid @RequestBody EtapaRequest request) {
        return ResponseEntity.ok(etapaService.updateEtapa(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtapa(@PathVariable Integer id) {
        etapaService.deleteEtapa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/especie/{especieId}")
    public ResponseEntity<List<EtapaDTO>> getEtapasByEspecie(@PathVariable Integer especieId) {
        return ResponseEntity.ok(etapaService.getEtapasByEspecie(especieId));
    }

    @PostMapping("/{etapaId}/reglas/{reglaId}")
    public ResponseEntity<EtapaDTO> addReglaToEtapa(@PathVariable Integer etapaId, @PathVariable Integer reglaId) {
        return ResponseEntity.ok(etapaService.addReglaToEtapa(etapaId, reglaId));
    }

    @DeleteMapping("/{etapaId}/reglas/{reglaId}")
    public ResponseEntity<EtapaDTO> removeReglaFromEtapa(@PathVariable Integer etapaId, @PathVariable Integer reglaId) {
        return ResponseEntity.ok(etapaService.removeReglaFromEtapa(etapaId, reglaId));
    }

    @GetMapping("/tipos")
    public TipoEtapa[] getTiposEtapas() {
        return TipoEtapa.values();
    }
}
