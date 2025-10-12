package com.cultivapp.cultivapp.services;

import com.cultivapp.cultivapp.Model.Clases.Especie;
import com.cultivapp.cultivapp.Model.Clases.Etapa;
import com.cultivapp.cultivapp.Model.Util.EspecieManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EtapaService {

    private List<Etapa> etapas = new ArrayList<>();

    // Inyección del EspecieService
    private final EspecieService especieService;

    // Constructor con inyección automática
    @Autowired
    public EtapaService(EspecieService especieService) {
        this.especieService = especieService;
    }

    public List<Etapa> ObtenerListEtapasPorId(int idEspecie) {
        Especie especie = especieService.ObtenerEspeciePorId(idEspecie);
        return especie != null ? especie.getL_etapas() : new ArrayList<>();
    }

    public Etapa ObtenerEtapaPorId(int idEtapa, int idEspecie) {
        return ObtenerListEtapasPorId(idEspecie).stream()
                .filter(etap -> etap.getId() == idEtapa)
                .findFirst()
                .orElse(null);
    }
}
