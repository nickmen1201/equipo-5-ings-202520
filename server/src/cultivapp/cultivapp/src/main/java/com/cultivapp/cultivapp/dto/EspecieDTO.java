package com.cultivapp.cultivapp.dto;

import lombok.*;

/**
 * DTO for Especie responses (REQ-005)
 * Used to transfer species data to frontend
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspecieDTO {
    private Integer id;
    private String nombre;
    private String nombreCientifico;
    private String descripcion;
    private Integer diasFertilizacion;
    private String imagenUrl;
    private Integer cicloDias;
    private Integer diasGerminacion;
    private Integer diasFloracion;
    private Integer diasCosecha;
    private Integer aguaSemanalMm;
    private Boolean activo;
}
