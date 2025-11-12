package com.cultivapp.cultivapp.dto;

import java.util.List;

import com.cultivapp.cultivapp.models.enums.TipoEtapa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EtapaDTO {
    private Integer id;
    private TipoEtapa nombre;
    private EspecieDTO especie;
    private Integer duracionDias;
    private Short orden;
    private List<ReglaRequest> reglas;
}