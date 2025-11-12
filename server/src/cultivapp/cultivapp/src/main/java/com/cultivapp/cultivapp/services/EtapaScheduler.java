package com.cultivapp.cultivapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EtapaScheduler {
    @Autowired
    private CultivoService cultivoService;

    // todos los días a la 1:00 AM
    @Scheduled(cron = "0 10 22 * * *", zone = "America/Bogota")
    public void verificarCambioDeEtapas() {
        System.out.println("⏰ Ejecutando verificación de etapas de cultivos...");
        cultivoService.verificarCambiosDeEtapa();
        System.out.println("✅ Verificación de etapas completada.");
    }
}
