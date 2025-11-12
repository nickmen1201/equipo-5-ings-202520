package com.cultivapp.cultivapp.controllers;

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
