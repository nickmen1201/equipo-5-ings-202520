package com.cultivapp.cultivapp.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cultivapp.cultivapp.models.enums.Estado;
import com.cultivapp.cultivapp.models.enums.EtapaActual;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Cultivo entity: represents a farmer's crop
@Entity
@Table(name = "cultivos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cultivo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    // Foreign key to usuarios table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "cultivos", "alertas", "tareas"})
    private Usuario usuario;
    
    // Foreign key to especies table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especie_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "etapas", "cultivos"})
    private Especie especie;
    
    @Column(nullable = false, length = 150)
    private String nombre;
    
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
    
    // Planting date - required by database
    @Column(name = "fecha_siembra", nullable = false)
    private LocalDate fechaSiembra;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    
    // Automatically set creation date before saving
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }
    
    // Automatically update modification date before updating
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}





