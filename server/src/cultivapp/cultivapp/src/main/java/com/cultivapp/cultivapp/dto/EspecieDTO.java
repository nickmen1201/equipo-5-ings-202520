package com.cultivapp.cultivapp.dto;

/**
 * DTO for Especie responses (REQ-005)
 * Used to transfer species data to frontend
 */
public class EspecieDTO {
    private Integer id;
    private String nombre;
    private String nombreCientifico;
    private String descripcion;
    private Integer diasFertilizacion;
    private String imagenUrl;
    private Integer cicloDias;
    private Integer diasGerminacion;
    private Integer diasFloracion;
    private Integer diasCosecha;
    private Integer aguaSemanalMm;
    private Boolean activo;
    
    // Constructors
    public EspecieDTO() {}
    
    public EspecieDTO(Integer id, String nombre, String nombreCientifico, String descripcion,
                     Integer diasFertilizacion, String imagenUrl, Integer cicloDias,
                     Integer diasGerminacion, Integer diasFloracion, Integer diasCosecha,
                     Integer aguaSemanalMm, Boolean activo) {
        this.id = id;
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
        this.activo = activo;
    }
    
    // Builder pattern
    public static EspecieDTOBuilder builder() {
        return new EspecieDTOBuilder();
    }
    
    public static class EspecieDTOBuilder {
        private Integer id;
        private String nombre;
        private String nombreCientifico;
        private String descripcion;
        private Integer diasFertilizacion;
        private String imagenUrl;
        private Integer cicloDias;
        private Integer diasGerminacion;
        private Integer diasFloracion;
        private Integer diasCosecha;
        private Integer aguaSemanalMm;
        private Boolean activo;
        
        public EspecieDTOBuilder id(Integer id) {
            this.id = id;
            return this;
        }
        
        public EspecieDTOBuilder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }
        
        public EspecieDTOBuilder nombreCientifico(String nombreCientifico) {
            this.nombreCientifico = nombreCientifico;
            return this;
        }
        
        public EspecieDTOBuilder descripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }
        
        public EspecieDTOBuilder diasFertilizacion(Integer diasFertilizacion) {
            this.diasFertilizacion = diasFertilizacion;
            return this;
        }
        
        public EspecieDTOBuilder imagenUrl(String imagenUrl) {
            this.imagenUrl = imagenUrl;
            return this;
        }
        
        public EspecieDTOBuilder cicloDias(Integer cicloDias) {
            this.cicloDias = cicloDias;
            return this;
        }
        
        public EspecieDTOBuilder diasGerminacion(Integer diasGerminacion) {
            this.diasGerminacion = diasGerminacion;
            return this;
        }
        
        public EspecieDTOBuilder diasFloracion(Integer diasFloracion) {
            this.diasFloracion = diasFloracion;
            return this;
        }
        
        public EspecieDTOBuilder diasCosecha(Integer diasCosecha) {
            this.diasCosecha = diasCosecha;
            return this;
        }
        
        public EspecieDTOBuilder aguaSemanalMm(Integer aguaSemanalMm) {
            this.aguaSemanalMm = aguaSemanalMm;
            return this;
        }
        
        public EspecieDTOBuilder activo(Boolean activo) {
            this.activo = activo;
            return this;
        }
        
        public EspecieDTO build() {
            return new EspecieDTO(id, nombre, nombreCientifico, descripcion, diasFertilizacion,
                                imagenUrl, cicloDias, diasGerminacion, diasFloracion, diasCosecha,
                                aguaSemanalMm, activo);
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
    
    public Boolean getActivo() {
        return activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
