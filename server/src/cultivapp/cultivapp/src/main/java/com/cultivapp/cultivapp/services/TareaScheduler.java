package com.cultivapp.cultivapp.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cultivapp.cultivapp.models.Cultivo;
import com.cultivapp.cultivapp.models.Etapa;
import com.cultivapp.cultivapp.models.Regla;
import com.cultivapp.cultivapp.models.Tarea;
import com.cultivapp.cultivapp.repositories.CultivoRepository;
import com.cultivapp.cultivapp.repositories.TareaRepository;
import com.cultivapp.cultivapp.services.strategies.EstrategiaRegla;

@Service
public class TareaScheduler {

    @Autowired
    private CultivoRepository cultivoRepository;

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private ReglaContext strategyFactory; 

    /**
     * Se ejecuta cada día (puedes ajustar el horario según lo necesites)
     * Aquí se actualizan tareas vencidas y se crean nuevas tareas.
     */
    @Transactional
    @Scheduled(cron = "0 12 22 * * *", zone = "America/Bogota") 
    public void generarTareas() {
        List<Cultivo> cultivos = cultivoRepository.findCultivosActivos();

        for (Cultivo cultivo : cultivos) {

            // === 1️⃣ ACTUALIZAR TAREAS VENCIDAS ===
            List<Tarea> tareasCultivo = tareaRepository.findByCultivoId(cultivo.getId());

            for (Tarea tarea : tareasCultivo) {
                if (!tarea.isRealizada() && tarea.getFechaVencimiento() != null &&
                    tarea.getFechaVencimiento().isBefore(LocalDateTime.now())) {

                    tarea.setVencida(true);
                    tarea.setActiva(false);

                    // Aplica la estrategia correspondiente (penalización)
                    EstrategiaRegla strategy = strategyFactory.getStrategy(tarea.getRegla().getTipo());
                    if (strategy != null) {
                        strategy.ejecutarTarea(tarea);
                    }
                }
            }
            tareaRepository.saveAll(tareasCultivo);

            // === 2️⃣ GENERAR NUEVAS TAREAS SEGÚN LAS REGLAS DE LA ETAPA ===
            List<Regla> reglasEtapa = cultivo.getEspecie()
                                            .getEtapas()
                                            .stream()
                                            .filter(e -> e.getOrden().equals(cultivo.getEtapaActual()))
                                            .findFirst()
                                            .map(Etapa::getReglas)
                                            .orElse(List.of());

            for (Regla regla : reglasEtapa) {
                LocalDateTime ultimaTarea = tareaRepository.findUltimaFecha(cultivo.getId(), regla.getId());

                // Si no existe tarea previa o ya pasó el intervalo
                if (ultimaTarea == null ||
                    ultimaTarea.plusDays(regla.getIntervaloDias()).isBefore(LocalDateTime.now())) {

                    System.out.println("⏰ Generando tarea para cultivo: " + cultivo.getNombre() + ", regla: " + regla.getDescripcion());

                    // Crear notificación
                    notificacionService.createNotificacion(
                        "Nueva tarea generada en el cultivo " + cultivo.getNombre(),
                        cultivo.getUsuario().getId()
                    );

                    // Crear nueva tarea
                    Tarea nueva = new Tarea();
                    nueva.setCultivo(cultivo);
                    nueva.setRegla(regla);
                    nueva.setFechaProgramada(LocalDateTime.now());
                    nueva.setActiva(true);
                    nueva.setVencida(false);

                    tareaRepository.save(nueva);
                }
            }

            // Guarda cambios en cultivo (por si alguna estrategia modificó su salud)
            cultivoRepository.save(cultivo);
        }
    }
}

