package com.cultivapp.cultivapp.Controllers;

import com.cultivapp.cultivapp.Model.Clases.Especie;
import com.cultivapp.cultivapp.Model.Clases.Etapa;
import com.cultivapp.cultivapp.Model.Util.EspecieManager;
import com.cultivapp.cultivapp.services.EspecieService;
import com.cultivapp.cultivapp.services.EtapaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequestMapping("/etapas") CAMBIAAAR
public class EtapaController {

    private final EspecieService especieService;
    private final EtapaService etapaService;

    public EtapaController(EtapaService etapaService, EspecieService especieService) {
        this.etapaService = etapaService;
        this.especieService = especieService;

    }

    @GetMapping("/etapas/{id}")
    public String Info(@PathVariable int id , Model model) {
        Especie especie = especieService.ObtenerEspeciePorId(id);//REVISAR, se esta usando un Manager en vez de un servicio
        model.addAttribute("nombreEspecie", especie.getNombreComun());
        model.addAttribute("idEspecie", id);
        List<Etapa> etapas = especie.getL_etapas();
        model.addAttribute("etapas", etapas);

        System.out.println("el id enviado fue: "+ especie.getId());
        return "ModificarEtapas";
    }

    @GetMapping("/etapas/modificar/{id}/{idEspecie}")
    public String ModificarEspecie(@PathVariable int id, @PathVariable int idEspecie, Model model) {
        // Busca la especie específica por su ID

        System.out.println("el id de la especie fue: "+ idEspecie);
        System.out.println("el id de la etapa fue: "+ id);


        Etapa etapa = etapaService.ObtenerEtapaPorId(id, idEspecie);
        model.addAttribute("etapa", etapa);
        model.addAttribute("idEspecie", idEspecie);


        return "FormularioModificarEtapa"; // nombre de tu nueva vista
    }

    @PostMapping("/etapas/actualizar/{idEspecie}")
    public String ActualizarEtapa(@ModelAttribute Etapa etapa, @PathVariable int idEspecie) {
        Etapa etapaOriginal = etapaService.ObtenerEtapaPorId(etapa.getId(), idEspecie);

        if (etapaOriginal != null) {

            // Actualiza solo los campos del formulario
            etapaOriginal.setNombreEtapa(etapa.getNombreEtapa());
            etapaOriginal.setDescripcionEtapa(etapa.getDescripcionEtapa());
            etapaOriginal.setDuracionDias(etapa.getDuracionDias());
            etapaOriginal.setObservaciones(etapa.getObservaciones());
            etapaOriginal.setRiesgo(etapa.getRiesgo());
            etapaOriginal.setFertilizacion(etapa.getFertilizacion());

            // Guarda los cambios
            especieService.GuardarCambios();
        }

        return "redirect:/especies";
    }
}
