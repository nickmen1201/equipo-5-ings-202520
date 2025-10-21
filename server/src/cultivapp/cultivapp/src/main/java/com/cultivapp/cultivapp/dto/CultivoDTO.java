package com.cultivapp.cultivapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cultivapp.cultivapp.model.enums.Estado;
import com.cultivapp.cultivapp.model.enums.EtapaActual;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CultivoDTO {
    private Integer id;
    private String nombre;
    private LocalDate fechaSiembra;
    private BigDecimal areaHectareas;
    private EtapaActual etapaActual;
    private Estado estado;
    private BigDecimal rendimientoKg;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    
    private String especieNombre;
    private Integer usuarioID;
}