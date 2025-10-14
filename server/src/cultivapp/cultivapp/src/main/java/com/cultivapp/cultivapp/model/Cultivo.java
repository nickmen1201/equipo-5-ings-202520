package com.cultivapp.cultivapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

// Cultivo entity: represents a farmer's crop
// Enum stored as string to keep database portable
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
    
    // Foreign key to usuarios table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    // Foreign key to especies table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especie_id", nullable = false)
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
