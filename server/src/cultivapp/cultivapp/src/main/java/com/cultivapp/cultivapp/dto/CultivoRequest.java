package com.cultivapp.cultivapp.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CultivoRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    @NotNull(message = "El área es obligatoria")
    @DecimalMin(value = "0.01", message = "El área debe ser mayor a 0")
    private BigDecimal areaHectareas;
    
    private BigDecimal rendimientoKg;
    private Short etapaActual;
    private String estado;
    private Integer usuarioId;
    private Integer especieId;
}