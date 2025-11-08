package com.cultivapp.cultivapp.models;

import com.cultivapp.cultivapp.models.enums.EtapaActual;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    private EtapaActual nombre;

    @ManyToOne
    @JoinColumn(name = "especie_id")
    private Especie especie;
}