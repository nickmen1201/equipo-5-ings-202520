package com.cultivapp.cultivapp.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "fecha_generacion")
    private LocalDateTime fechaGeneracion;

    @ManyToOne
    @JoinColumn(name="etapa_id")
    private Etapa etapa;
    
    // Automatically set generation date before saving
    @PrePersist
    protected void onCreate() {
        fechaGeneracion = LocalDateTime.now();
    }
}
