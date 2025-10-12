package com.cultivapp.cultivapp.Controllers;

import com.cultivapp.cultivapp.Model.Clases.Especie;
import com.cultivapp.cultivapp.Model.Util.EspecieManager;
import com.cultivapp.cultivapp.services.EspecieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/especies")  // Prefijo común para todas las rutas
public class EspecieController {

    // Inyección de dependencias (Spring crea e inyecta el servicio automáticamente)
    @Autowired
    private EspecieService especieService;

    @GetMapping
    public String listarEspecies(Model model) {
        List<Especie> especies = especieService.ObtenerTodasLasEspecies();
        model.addAttribute("especies", especies);
        return "ModificarEspecies";
    }

    @GetMapping("/modificar/{id}")
    public String mostrarFormularioModificar(@PathVariable int id, Model model) {
        Especie especie = especieService.ObtenerEspeciePorId(id);
        model.addAttribute("especie", especie);

        // Agrega los enums al modelo
        model.addAttribute("tiposEspecie", EspecieManager.TipoEspecie.values());
        model.addAttribute("ciclos", EspecieManager.Ciclo.values());
        model.addAttribute("suelos", EspecieManager.Suelos.values());

        return "FormularioModificarEspecie";
    }

    @PostMapping("/actualizar")
    public String actualizarEspecie(@ModelAttribute Especie especieForm) {
        Especie especieOriginal = especieService.ObtenerEspeciePorId(especieForm.getId());

        if (especieOriginal != null) {
            // Actualiza solo los campos del formulario
            especieOriginal.setNombreComun(especieForm.getNombreComun());
            especieOriginal.setNombreCientifico(especieForm.getNombreCientifico());
            especieOriginal.setTipoEspecie(especieForm.getTipoEspecie());
            especieOriginal.setCiclo(especieForm.getCiclo());
            especieOriginal.setSueloIdeal(especieForm.getSueloIdeal());
            especieOriginal.setPhMax(especieForm.getPhMax());
            especieOriginal.setPhMin(especieForm.getPhMin());
            especieOriginal.setAltitudOptima(especieForm.getAltitudOptima());
            especieOriginal.setTemperaturaOptima(especieForm.getTemperaturaOptima());
            especieOriginal.setDiasGerminacion(especieForm.getDiasGerminacion());
            especieOriginal.setDiasFloracion(especieForm.getDiasFloracion());
            especieOriginal.setDiasCosecha(especieForm.getDiasCosecha());
            especieOriginal.setAguaSemanalmm(especieForm.getAguaSemanalmm());

            // Guarda los cambios
            especieService.GuardarCambios();
        }

        return "redirect:/especies";
    }
}
