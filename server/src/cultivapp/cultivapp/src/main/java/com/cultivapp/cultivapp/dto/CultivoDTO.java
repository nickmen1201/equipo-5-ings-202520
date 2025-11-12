package com.cultivapp.cultivapp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cultivapp.cultivapp.models.enums.Estado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CultivoDTO {
    private Integer id;
    private String nombre;
    private BigDecimal areaHectareas;
    private Short etapaActual;
    private Estado estado;
    private BigDecimal rendimientoKg;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    
    private String especieNombre;
    private String especieImagenUrl;
    private String especieDescripcion;
    private int totalEtapas;
    private Integer usuarioID;
    private String usuarioCiudad;
}