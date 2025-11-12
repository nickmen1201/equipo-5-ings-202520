package com.cultivapp.cultivapp.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Especie responses (REQ-005)
 * Used to transfer species data to frontend
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EspecieDTO {
    private Integer id;
    private String nombre;
    private String nombreCientifico;
    private String descripcion;
    private String imagenUrl;
    private Integer cicloDias;
    private Integer aguaSemanalMm;
    private Boolean activo;
    private List<EtapaDTO> etapas;
}
