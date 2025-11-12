package com.cultivapp.cultivapp.services;

import jakarta.transaction.Transactional;

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