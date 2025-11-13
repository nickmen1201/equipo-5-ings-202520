package com.cultivapp.cultivapp.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating/updating Especie (REQ-005)
 * Contains validation rules for species data
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EspecieRequest {
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    private String nombreCientifico;
    
    private String descripcion;
    
    private String imagenUrl;
    
    private Integer aguaSemanalMm;

    @NotNull(message = "La lista de etapas es obligatoria")
    @Size(min = 3, message = "Debe especificar al menos 3 etapas para la especie")
    @Valid
    private List<EtapaRequest> etapas;
}
