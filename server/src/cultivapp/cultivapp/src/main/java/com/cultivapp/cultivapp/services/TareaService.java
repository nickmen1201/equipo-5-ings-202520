package com.cultivapp.cultivapp.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cultivapp.cultivapp.models.Tarea;
import com.cultivapp.cultivapp.repositories.CultivoRepository;
import com.cultivapp.cultivapp.repositories.TareaRepository;
import com.cultivapp.cultivapp.services.strategies.EstrategiaRegla;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class TareaService {

    private final ReglaContext strategyFactory;
    private final TareaRepository tareaRepository;
    private final CultivoRepository cultivoRepository;

    @Transactional
    public void ejecutarTarea(Integer tareaId) {
        Tarea tarea = tareaRepository.findById(tareaId)
            .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        EstrategiaRegla strategy = strategyFactory.getStrategy(tarea.getRegla().getTipo());

        if (strategy != null) {
            strategy.ejecutarTarea(tarea);
        }

        tarea.setRealizada(true);
        tarea.setActiva(false);

        cultivoRepository.save(tarea.getCultivo());
        tareaRepository.save(tarea);
    }
}