package com.cultivapp.cultivapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cultivapp.cultivapp.model.enums.Estado;
import com.cultivapp.cultivapp.model.enums.EtapaActual;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CultivoDetailDTO {
    private Integer id;
    private String nombre;
    private LocalDate fechaSiembra;
    private BigDecimal areaHectareas;
    private EtapaActual etapaActual;
    private Estado estado;
    private BigDecimal rendimientoKg;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    
    private EspecieInfo especie;
    private UsuarioInfo usuario;
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EspecieInfo {
        private Integer id;
        private String nombre;
        private String nombreCientifico;
        private String imagenUrl;
        private Integer cicloDias;
    }
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UsuarioInfo {
        private Integer id;
        private String email;
    }
}
