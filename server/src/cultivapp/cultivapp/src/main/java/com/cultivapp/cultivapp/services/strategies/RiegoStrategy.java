package com.cultivapp.cultivapp.services.strategies;

import org.springframework.stereotype.Component;

import com.cultivapp.cultivapp.models.Cultivo;
import com.cultivapp.cultivapp.models.Tarea;

@Component
public class RiegoStrategy implements EstrategiaRegla {
	@Override
    public void ejecutarTarea(Tarea tarea) {
        Cultivo cultivo = tarea.getCultivo();

        if (tarea.isVencida()) {
            cultivo.setSaludRiego(Math.max(0, cultivo.getSaludRiego() - 10)); // baja 10 puntos
        } else if (tarea.isActiva()) {
            cultivo.setSaludRiego(Math.min(100, cultivo.getSaludRiego() + 5)); // sube 5 puntos
        }
    }
}
