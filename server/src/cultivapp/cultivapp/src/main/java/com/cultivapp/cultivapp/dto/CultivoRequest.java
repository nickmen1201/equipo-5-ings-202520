package com.cultivapp.cultivapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.cultivapp.cultivapp.models.enums.Estado;
import com.cultivapp.cultivapp.models.enums.EtapaActual;

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
    
    private EtapaActual etapaActual;
    private Estado estado;
    private BigDecimal rendimientoKg;
    private LocalDate fechaSiembra; // Optional - defaults to today if not provided
    private Integer usuarioId;
    private Integer especieId;
}
