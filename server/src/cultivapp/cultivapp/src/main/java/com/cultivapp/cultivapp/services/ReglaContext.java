package com.cultivapp.cultivapp.services;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.cultivapp.cultivapp.models.enums.TipoRegla;
import com.cultivapp.cultivapp.services.strategies.EstrategiaRegla;

@Component
public class ReglaContext {
	

	private final Map<TipoRegla, EstrategiaRegla> strategies;

    public ReglaContext(
        RiegoStrategy riego,
        FertilizacionStrategy fertilizacion,
        MantenimientoStrategy mantenimiento
    ) {
        strategies = Map.of(
            TipoRegla.RIEGO, riego,
            TipoRegla.FERTILIZACION, fertilizacion,
            TipoRegla.MANTENIMIENTO, mantenimiento
        );
    }

    public EstrategiaRegla getStrategy(TipoRegla tipo) {
        return strategies.get(tipo);
    }
}
