package com.cultivapp.cultivapp.services.strategies;

import org.springframework.stereotype.Component;

import com.cultivapp.cultivapp.models.Cultivo;
import com.cultivapp.cultivapp.models.Tarea;

@Component
public class MantenimientoStrategy implements EstrategiaRegla{

	@Override
    public void ejecutarTarea(Tarea tarea) {
        Cultivo cultivo = tarea.getCultivo();

        if (tarea.isVencida()) {
            cultivo.setSaludMantenimiento(Math.max(0, cultivo.getSaludMantenimiento() - 6));
        } else if (tarea.isActiva()) {
            cultivo.setSaludMantenimiento(Math.min(100, cultivo.getSaludMantenimiento() + 3));
        }
    }
}
 