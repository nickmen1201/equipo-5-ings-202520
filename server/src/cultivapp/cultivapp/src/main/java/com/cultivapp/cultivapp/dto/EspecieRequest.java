package com.cultivapp.cultivapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

/**
 * DTO for creating/updating Especie (REQ-005)
 * Contains validation rules for species data
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
