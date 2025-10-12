package com.cultivapp.cultivapp.Model.Clases;



import com.cultivapp.cultivapp.Model.Util.EspecieManager;
import com.cultivapp.cultivapp.Model.Util.UsuarioManager;
import jakarta.validation.constraints.*;

import java.util.List;

public class Especie {

    private int id;
    private List<Etapa> l_etapas; // revisar

    @NotBlank(message = "El nombre común es obligatorio")
    @Size(min = 3, max = 50, message = "Debe tener entre 3 y 50 caracteres")
    private String nombreComun; //

    @NotBlank(message = "El nombre científico es obligatorio")
    @Size(min = 3, max = 100, message = "Debe tener entre 3 y 100 caracteres")
    private String nombreCientifico;//

    @NotNull(message = "Debe seleccionar un tipo de especie")
    private EspecieManager.TipoEspecie tipoEspecie; //revisar

    @NotNull(message = "Debe seleccionar un ciclo")
    private EspecieManager.Ciclo ciclo; //revisar

    @NotNull(message = "Debe seleccionar un tipo de suelo")
    private EspecieManager.Suelos sueloIdeal; //revisar

    @NotNull(message = "El pH máximo es obligatorio")
    @DecimalMin(value = "0.0", message = "El pH debe ser mayor o igual a 0")
    @DecimalMax(value = "14.0", message = "El pH debe ser menor o igual a 14")
    private float phMax;//

    @NotNull(message = "El pH mínimo es obligatorio")
    @DecimalMin(value = "0.0", message = "El pH debe ser mayor o igual a 0")
    @DecimalMax(value = "14.0", message = "El pH debe ser menor o igual a 14")
    private float phMin;//

    @NotNull(message = "La altitud óptima es obligatoria")
    @Min(value = 0, message = "La altitud no puede ser negativa")
    @Max(value = 6000, message = "La altitud no puede superar los 6000 msnm")
    private int altitudOptima; //

    @NotNull(message = "La temperatura óptima es obligatoria")
    @Min(value = -10, message = "La temperatura mínima es -10°C")
    @Max(value = 50, message = "La temperatura máxima es 50°C")
    private int temperaturaOptimaCentigrados;

    @NotNull(message = "Los días de germinación son obligatorios")
    @Min(value = 1, message = "Debe ser al menos 1 día")
    @Max(value = 365, message = "No puede superar 365 días")
    private int diasGerminacion;

    @NotNull(message = "Los días de floración son obligatorios")
    @Min(value = 1, message = "Debe ser al menos 1 día")
    @Max(value = 365, message = "No puede superar 365 días")
    private int diasFloracion;

    @NotNull(message = "Los días de cosecha son obligatorios")
    @Min(value = 1, message = "Debe ser al menos 1 día")
    @Max(value = 730, message = "No puede superar 730 días (2 años)")
    private int diasCosecha;

    @NotNull(message = "El agua semanal es obligatoria")
    @Min(value = 0, message = "El agua no puede ser negativa")
    @Max(value = 500, message = "El agua no puede superar 500mm")
    private int aguaSemanalmm;

    private static int ides = 0;

    public Especie(){}

    //Constructor
    public Especie(List<Etapa> l_etapas, String nombreComun, String nombreCientifico,
            EspecieManager.TipoEspecie tipoEspecie, EspecieManager.Ciclo ciclo, EspecieManager.Suelos sueloIdeal,
            float phMax, float phMin, int altitudOptima, int temperaturaOptima, int diasGerminacion, int diasFloracion,
            int diasCosecha, int aguaSemanalmm){
        id = ides;
        this.l_etapas = l_etapas;
        this.nombreComun = nombreComun;
        this.nombreCientifico = nombreCientifico;
        this.tipoEspecie = tipoEspecie;
        this.ciclo = ciclo;
        this.sueloIdeal = sueloIdeal;
        this.phMax = phMax;
        this.phMin = phMin;
        this.altitudOptima = altitudOptima;
        this.temperaturaOptimaCentigrados = temperaturaOptima;
        this.diasGerminacion = diasGerminacion;
        this.diasFloracion = diasFloracion;
        this.diasCosecha = diasCosecha;
        this.aguaSemanalmm = aguaSemanalmm;


        ides++;
    }


    //Getters
    public int getId() { return id; }

    public List<Etapa> getL_etapas() {
        return l_etapas;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public EspecieManager.TipoEspecie getTipoEspecie() {
        return tipoEspecie;
    }

    public EspecieManager.Ciclo getCiclo() {
        return ciclo;
    }

    public EspecieManager.Suelos getSueloIdeal() {
        return sueloIdeal;
    }

    public float getPhMax() {
        return phMax;
    }

    public float getPhMin() {
        return phMin;
    }

    public int getAltitudOptima() {
        return altitudOptima;
    }

    public int getTemperaturaOptima() {
        return temperaturaOptimaCentigrados;
    }

    public int getDiasGerminacion() {
        return diasGerminacion;
    }

    public int getDiasFloracion() {
        return diasFloracion;
    }

    public int getDiasCosecha() {
        return diasCosecha;
    }

    public int getAguaSemanalmm() {
        return aguaSemanalmm;
    }


    //Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setL_etapas(List<Etapa> l_etapas) {
        this.l_etapas = l_etapas;
    }

    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
    }

    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }

    public void setTipoEspecie(EspecieManager.TipoEspecie tipoEspecie) {
        this.tipoEspecie = tipoEspecie;
    }

    public void setCiclo(EspecieManager.Ciclo ciclo) {
        this.ciclo = ciclo;
    }

    public void setSueloIdeal(EspecieManager.Suelos sueloIdeal) {
        this.sueloIdeal = sueloIdeal;
    }

    public void setPhMax(float phMax) {
        this.phMax = phMax;
    }

    public void setPhMin(float phMin) {
        this.phMin = phMin;
    }

    public void setAltitudOptima(int altitudOptima) {
        this.altitudOptima = altitudOptima;
    }

    public void setTemperaturaOptima(int temperaturaOptima) {
        this.temperaturaOptimaCentigrados = temperaturaOptima;
    }

    public void setDiasGerminacion(int diasGerminacion) {
        this.diasGerminacion = diasGerminacion;
    }

    public void setDiasFloracion(int diasFloracion) {
        this.diasFloracion = diasFloracion;
    }

    public void setDiasCosecha(int diasCosecha) {
        this.diasCosecha = diasCosecha;
    }

    public void setAguaSemanalmm(int aguaSemanalmm) {
        this.aguaSemanalmm = aguaSemanalmm;
    }
}
