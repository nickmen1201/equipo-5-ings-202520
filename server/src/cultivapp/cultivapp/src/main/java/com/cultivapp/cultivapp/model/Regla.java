package com.cultivapp.cultivapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

// Regla entity: represents an alert rule for a specific growth stage
@Entity
@Table(name = "reglas")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Regla {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    // Foreign key to etapas table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etapa_id", nullable = false)
    private Etapa etapa;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "fecha_generacion")
    private LocalDateTime fechaGeneracion;
    
    // Automatically set generation date before saving
    @PrePersist
    protected void onCreate() {
        fechaGeneracion = LocalDateTime.now();
    }
}
