package com.cultivapp.cultivapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO for creating/updating Especie (REQ-005)
 * Contains validation rules for species data
 */
public class EspecieRequest {
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    private String nombreCientifico;
    
    private String descripcion;
    
    @Positive(message = "Los días de fertilización deben ser positivos")
    private Integer diasFertilizacion;
    
    private String imagenUrl;
    
    @NotNull(message = "El ciclo de días es obligatorio")
    @Positive(message = "El ciclo de días debe ser positivo")
    private Integer cicloDias;
    
    @NotNull(message = "Los días de germinación son obligatorios")
    @Positive(message = "Los días de germinación deben ser positivos")
    private Integer diasGerminacion;
    
    @NotNull(message = "Los días de floración son obligatorios")
    @Positive(message = "Los días de floración deben ser positivos")
    private Integer diasFloracion;
    
    @NotNull(message = "Los días de cosecha son obligatorios")
    @Positive(message = "Los días de cosecha deben ser positivos")
    private Integer diasCosecha;
    
    private Integer aguaSemanalMm;
    
    // Constructors
    public EspecieRequest() {}
    
    public EspecieRequest(String nombre, String nombreCientifico, String descripcion, 
                         Integer diasFertilizacion, String imagenUrl, Integer cicloDias,
                         Integer diasGerminacion, Integer diasFloracion, Integer diasCosecha,
                         Integer aguaSemanalMm) {
        this.nombre = nombre;
        this.nombreCientifico = nombreCientifico;
        this.descripcion = descripcion;
        this.diasFertilizacion = diasFertilizacion;
        this.imagenUrl = imagenUrl;
        this.cicloDias = cicloDias;
        this.diasGerminacion = diasGerminacion;
        this.diasFloracion = diasFloracion;
        this.diasCosecha = diasCosecha;
        this.aguaSemanalMm = aguaSemanalMm;
    }
    
    // Getters and Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombreCientifico() {
        return nombreCientifico;
    }
    
    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Integer getDiasFertilizacion() {
        return diasFertilizacion;
    }
    
    public void setDiasFertilizacion(Integer diasFertilizacion) {
        this.diasFertilizacion = diasFertilizacion;
    }
    
    public String getImagenUrl() {
        return imagenUrl;
    }
    
    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
    
    public Integer getCicloDias() {
        return cicloDias;
    }
    
    public void setCicloDias(Integer cicloDias) {
        this.cicloDias = cicloDias;
    }
    
    public Integer getDiasGerminacion() {
        return diasGerminacion;
    }
    
    public void setDiasGerminacion(Integer diasGerminacion) {
        this.diasGerminacion = diasGerminacion;
    }
    
    public Integer getDiasFloracion() {
        return diasFloracion;
    }
    
    public void setDiasFloracion(Integer diasFloracion) {
        this.diasFloracion = diasFloracion;
    }
    
    public Integer getDiasCosecha() {
        return diasCosecha;
    }
    
    public void setDiasCosecha(Integer diasCosecha) {
        this.diasCosecha = diasCosecha;
    }
    
    public Integer getAguaSemanalMm() {
        return aguaSemanalMm;
    }
    
    public void setAguaSemanalMm(Integer aguaSemanalMm) {
        this.aguaSemanalMm = aguaSemanalMm;
    }
}
