package com.cultivapp.cultivapp.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Especie entity: represents a plant species in the catalog
@Entity
@Table(name = "especies")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Especie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "nombre_cientifico", length = 150)
    private String nombreCientifico;

    
    @Column(name = "ciclo_dias", nullable = false)
    private Integer cicloDias;
    
    @Column(name = "dias_germinacion", nullable = false)
    private Integer diasGerminacion;
    
    @Column(name = "dias_floracion", nullable = false)
    private Integer diasFloracion;
    
    @Column(name = "dias_cosecha", nullable = false)
    private Integer diasCosecha;
    
    @Column(name = "agua_semanal_mm")
    private Integer aguaSemanalMm;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "dias_fertilizacion")
    private Integer diasFertilizacion;
    
    @Column(name = "imagen_url", length = 500)
    private String imagenUrl;
    
    @Column(nullable = false)
    private Boolean activo;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "especie", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Etapa> etapas = new ArrayList<>();
    
    // Automatically set creation date before saving
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
    
}
