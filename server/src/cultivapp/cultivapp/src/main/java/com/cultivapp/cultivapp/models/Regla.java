package com.cultivapp.cultivapp.models;

import java.time.LocalDateTime;

import com.cultivapp.cultivapp.models.enums.TipoRegla;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Regla entity: represents a global alert rule (not tied to specific growth stage)
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

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoRegla tipo;

    @Column(name = "intervalo_dias")
    private Integer intervaloDias;

    
    // Automatically set generation date before saving
    @PrePersist
    protected void onCreate() {
        fechaGeneracion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        // nothing for now, but kept for future use
    }
}
