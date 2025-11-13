package com.cultivapp.cultivapp.models;

import java.util.ArrayList;
import java.util.List;

import com.cultivapp.cultivapp.models.enums.TipoEtapa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Etapa entity: represents a crop growth stage
@Entity
@Table(name = "etapas")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Etapa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre")
    private TipoEtapa nombre;

    @ManyToOne
    @JoinColumn(name = "especie_id")
    private Especie especie;

    @Column(name="duracion_dias")
    private Integer duracionDias;

    @Column(name="orden")
    private Short orden;

    @Builder.Default
    @ManyToMany
    @JoinTable(
        name = "etapas_reglas",
        joinColumns = @JoinColumn(name = "etapa_id"),
        inverseJoinColumns = @JoinColumn(name = "regla_id")
    )
    private List<Regla> reglas = new ArrayList<>();
}