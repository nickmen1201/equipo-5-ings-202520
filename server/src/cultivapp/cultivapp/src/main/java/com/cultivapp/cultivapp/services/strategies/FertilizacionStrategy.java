package com.cultivapp.cultivapp.services.strategies;

import org.springframework.stereotype.Component;

import com.cultivapp.cultivapp.models.Cultivo;
import com.cultivapp.cultivapp.models.Tarea;

@Component
public class FertilizacionStrategy implements EstrategiaRegla {

	@Override
    public void ejecutarTarea(Tarea tarea) {
        Cultivo cultivo = tarea.getCultivo();

        if (tarea.isVencida()) {
            cultivo.setSaludFertilizacion(Math.max(0, cultivo.getSaludFertilizacion() - 8));
        } else if (tarea.isActiva()) {
            cultivo.setSaludFertilizacion(Math.min(100, cultivo.getSaludFertilizacion() + 4));
        }
    }
}
