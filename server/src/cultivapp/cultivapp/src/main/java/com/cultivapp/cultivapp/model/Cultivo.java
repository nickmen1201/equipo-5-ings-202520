package com.cultivapp.cultivapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cultivapp.cultivapp.model.enums.Estado;
import com.cultivapp.cultivapp.model.enums.EtapaActual;

@Entity
@Table(name = "cultivos")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cultivo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuario usuario;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especie_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Especie especie;
    
    @Column(nullable = false, length = 150)
    private String nombre;
    
    @Column(name = "fecha_siembra", nullable = false)
    private LocalDate fechaSiembra;
    
    @Column(name = "area_hectareas", precision = 6, scale = 2)
    private BigDecimal areaHectareas;
    
    // Enum stored as string for readability
    @Enumerated(EnumType.STRING)
    @Column(name = "etapa_actual")
    private EtapaActual etapaActual;
    
    // Enum stored as string for readability
    @Enumerated(EnumType.STRING)
    @Column
    private Estado estado;
    
    @Column(name = "rendimiento_kg", precision = 8, scale = 2)
    private BigDecimal rendimientoKg;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
