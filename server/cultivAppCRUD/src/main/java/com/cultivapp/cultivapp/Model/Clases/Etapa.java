package com.cultivapp.cultivapp.Model.Clases;


import com.cultivapp.cultivapp.Model.Util.UsuarioManager;
import jakarta.validation.constraints.*;

import java.util.List;

public class Etapa {

    private int id;
    private List<Regla> l_reglas;

    @NotBlank(message = "El nombre de la etapa es obligatorio")
    @Size(min = 3, max = 50, message = "Debe tener entre 3 y 50 caracteres")
    private String nombreEtapa;

    @NotBlank(message = "La descripcion de la etapa es obligatorio")
    @Size(min = 3, max = 300, message = "Debe tener entre 3 y 300 caracteres")
    private String descripcionEtapa;

    @NotNull(message = "La duracion en dias es obligatoria")
    @Min(value = 0, message = "La duracion no puede ser negativa")
    private int duracionDias;

    @NotBlank(message = "Las observaciones son obligatorio")
    @Size(min = 3, max = 300, message = "Debe tener entre 3 y 300 caracteres")
    private String observaciones;

    @NotBlank(message = "El riesgo de la etapa es obligatorio")
    @Size(min = 3, max = 50, message = "Debe tener entre 3 y 50 caracteres")
    private String riesgo;

    @NotBlank(message = "La fertilizacion de la etapa es obligatorio")
    @Size(min = 3, max = 300, message = "Debe tener entre 3 y 300 caracteres")
    private String fertilizacion;

    public Etapa(List<Regla> reglas, String nombreEtapa, String descripcionEtapa, int duracionDias, String observaciones, String riesgo, String fertilizacion ) {
        this.l_reglas = reglas;
        this.nombreEtapa = nombreEtapa;
        this.descripcionEtapa = descripcionEtapa;
        this.duracionDias = duracionDias;
        this.observaciones = observaciones;
        this.riesgo = riesgo;
        this.fertilizacion = fertilizacion;

    }

    //Getters
    public int getId() {
        return id;
    }

    public String getNombreEtapa() {
        return nombreEtapa;
    }

    public List<Regla> getL_reglas() {
        return l_reglas;
    }

    public String getDescripcionEtapa() {
        return descripcionEtapa;
    }

    public int getDuracionDias() {
        return duracionDias;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public String getRiesgo() {
        return riesgo;
    }

    public String getFertilizacion() {
        return fertilizacion;
    }


    //Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setL_reglas(List<Regla> l_reglas) {
        this.l_reglas = l_reglas;
    }

    public void setNombreEtapa(String nombreEtapa) {
        this.nombreEtapa = nombreEtapa;
    }

    public void setDescripcionEtapa(String descripcionEtapa) {
        this.descripcionEtapa = descripcionEtapa;
    }

    public void setDuracionDias(int duracionDias) {
        this.duracionDias = duracionDias;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setRiesgo(String riesgo) {
        this.riesgo = riesgo;
    }

    public void setFertilizacion(String fertilizacion) {
        this.fertilizacion = fertilizacion;
    }
}
