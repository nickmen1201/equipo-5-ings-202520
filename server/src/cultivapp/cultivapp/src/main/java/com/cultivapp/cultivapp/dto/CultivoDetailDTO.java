package com.cultivapp.cultivapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.cultivapp.cultivapp.models.enums.Estado;

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
    private Short etapaActual;
    private Estado estado;
    private BigDecimal rendimientoKg;
    private double saludRiego;
    private double saludMantenimiento;
    private double saludFertilizacion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private EtapaInfo etapaActualInfo;
    private List<TareaInfo> tareas;
   
    
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
        private int totalEtapas;
       
    }

   @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EtapaInfo {
        private Integer id;
        private String nombre;
        private Integer duracionDias;
        private Short orden;
        private List<ReglaInfo> reglas; 
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReglaInfo {
        private Integer id;
        private String tipo;
        private String descripcion;
        private Integer intervaloDias;
    }
    
    @Data
    @Builder 
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UsuarioInfo {
        private Integer id;
        private String email;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TareaInfo {
        private Integer id;
        private String descripcionRegla;
        private boolean activa;
        private boolean realizada;
        private boolean vencida;
        private LocalDateTime fechaCreacion;
        private LocalDateTime fechaProgramada;
        private LocalDateTime fechaVencimiento;
    }
}
