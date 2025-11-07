package com.cultivapp.cultivapp.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cultivapp.cultivapp.dto.ReglaRequest;
import com.cultivapp.cultivapp.models.Regla;
import com.cultivapp.cultivapp.services.ReglaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/reglas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReglaController {

    private final ReglaService reglaService;


    //get all rules
    @GetMapping()
    public ResponseEntity<List<Regla>> getAllReglas(){

        List<Regla> reglas = reglaService.listAll();
        return ResponseEntity.ok(reglas);

    }

    @PostMapping()
    public ResponseEntity<Regla> CreateRegla(@Valid @RequestBody ReglaRequest request){

        Regla regla = reglaService.createRegla(request);
        return ResponseEntity.ok(regla);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEspecie(@PathVariable Integer id) {
        reglaService.deleteRegla(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tipos")
    public TipoRegla[] getTiposRegla() {
        return TipoRegla.values();
    }

    //actualizar 
    //filtros
        
}
