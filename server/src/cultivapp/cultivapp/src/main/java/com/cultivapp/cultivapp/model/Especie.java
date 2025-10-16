package com.cultivapp.cultivapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// Especie entity: represents a plant species in the catalog
@Entity
@Table(name = "especies")
public class Especie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "nombre_cientifico", length = 150)
    private String nombreCientifico;
    
    @Column(name = "ciclo_dias", nullable = false)
    private Integer cicloDias;
    
    @Column(name = "dias_germinacion", nullable = false)
    private Integer diasGerminacion;
    
    @Column(name = "dias_floracion", nullable = false)
    private Integer diasFloracion;
    
    @Column(name = "dias_cosecha", nullable = false)
    private Integer diasCosecha;
    
    @Column(name = "agua_semanal_mm")
    private Integer aguaSemanalMm;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "dias_fertilizacion")
    private Integer diasFertilizacion;
    
    @Column(name = "imagen_url", length = 500)
    private String imagenUrl;
    
    @Column(nullable = false)
    private Boolean activo;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    // Automatically set creation date before saving
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
    
    // No-arg constructor
    public Especie() {}
    
    // All-args constructor
    public Especie(Integer id, String nombre, String nombreCientifico, Integer cicloDias,
                   Integer diasGerminacion, Integer diasFloracion, Integer diasCosecha,
                   Integer aguaSemanalMm, String descripcion, Integer diasFertilizacion,
                   String imagenUrl, Boolean activo, LocalDateTime fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.nombreCientifico = nombreCientifico;
        this.cicloDias = cicloDias;
        this.diasGerminacion = diasGerminacion;
        this.diasFloracion = diasFloracion;
        this.diasCosecha = diasCosecha;
        this.aguaSemanalMm = aguaSemanalMm;
        this.descripcion = descripcion;
        this.diasFertilizacion = diasFertilizacion;
        this.imagenUrl = imagenUrl;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
    }
    
    // Builder pattern
    public static EspecieBuilder builder() {
        return new EspecieBuilder();
    }
    
    public static class EspecieBuilder {
        private Integer id;
        private String nombre;
        private String nombreCientifico;
        private Integer cicloDias;
        private Integer diasGerminacion;
        private Integer diasFloracion;
        private Integer diasCosecha;
        private Integer aguaSemanalMm;
        private String descripcion;
        private Integer diasFertilizacion;
        private String imagenUrl;
        private Boolean activo;
        private LocalDateTime fechaCreacion;
        
        public EspecieBuilder id(Integer id) {
            this.id = id;
            return this;
        }
        
        public EspecieBuilder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }
        
        public EspecieBuilder nombreCientifico(String nombreCientifico) {
            this.nombreCientifico = nombreCientifico;
            return this;
        }
        
        public EspecieBuilder cicloDias(Integer cicloDias) {
            this.cicloDias = cicloDias;
            return this;
        }
        
        public EspecieBuilder diasGerminacion(Integer diasGerminacion) {
            this.diasGerminacion = diasGerminacion;
            return this;
        }
        
        public EspecieBuilder diasFloracion(Integer diasFloracion) {
            this.diasFloracion = diasFloracion;
            return this;
        }
        
        public EspecieBuilder diasCosecha(Integer diasCosecha) {
            this.diasCosecha = diasCosecha;
            return this;
        }
        
        public EspecieBuilder aguaSemanalMm(Integer aguaSemanalMm) {
            this.aguaSemanalMm = aguaSemanalMm;
            return this;
        }
        
        public EspecieBuilder descripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }
        
        public EspecieBuilder diasFertilizacion(Integer diasFertilizacion) {
            this.diasFertilizacion = diasFertilizacion;
            return this;
        }
        
        public EspecieBuilder imagenUrl(String imagenUrl) {
            this.imagenUrl = imagenUrl;
            return this;
        }
        
        public EspecieBuilder activo(Boolean activo) {
            this.activo = activo;
            return this;
        }
        
        public EspecieBuilder fechaCreacion(LocalDateTime fechaCreacion) {
            this.fechaCreacion = fechaCreacion;
            return this;
        }
        
        public Especie build() {
            return new Especie(id, nombre, nombreCientifico, cicloDias, diasGerminacion,
                             diasFloracion, diasCosecha, aguaSemanalMm, descripcion,
                             diasFertilizacion, imagenUrl, activo, fechaCreacion);
        }
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
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
    
    public Boolean getActivo() {
        return activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
