package com.cultivapp.cultivapp.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tareas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TareaController {
    
    private final tareaService tareaService;

    @PostMapping("/ejecutar")
    public void ejecutarTarea(@RequestBody Integer tareaId) {
        tareaService.ejecutarTarea(tareaId);
    }
}