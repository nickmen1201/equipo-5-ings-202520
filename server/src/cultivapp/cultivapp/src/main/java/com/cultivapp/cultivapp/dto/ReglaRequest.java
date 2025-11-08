package com.cultivapp.cultivapp.dto;



import com.cultivapp.cultivapp.models.enums.TipoRegla;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReglaRequest {

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    private TipoRegla tipo;

    @Min(value = 1, message = "El intervalo en días debe ser al menos 1")
    private Integer intervaloDias;

    
}
     


