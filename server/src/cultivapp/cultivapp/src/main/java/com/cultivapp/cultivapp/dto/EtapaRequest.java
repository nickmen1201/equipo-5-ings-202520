package com.cultivapp.cultivapp.dto;

import java.util.List;

import com.cultivapp.cultivapp.models.enums.TipoEtapa;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EtapaRequest {
    
    @NotNull(message = "El nombre de la etapa es obligatorio")
    private TipoEtapa nombre;
    
    
    private Integer especieId;
    
    @NotNull(message = "La duración en días es obligatoria")
    // @Min(value = 1, message = "La duración debe ser al menos 1 día")
    private Integer duracionDias;
    
    @NotNull(message = "El orden es obligatorio")
    @Min(value = 1, message = "El orden debe ser al menos 1")
    private Short orden;
    
    @NotNull(message = "La lista de reglas es obligatoria")
    @Size(min = 1, message = "Debe especificar al menos 1 regla para la etapa")
    private List<Integer> reglaIds;
}
