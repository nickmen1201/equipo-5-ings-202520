package com.cultivapp.cultivapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

// Alerta entity: represents an alert generated for a crop based on a rule
@Entity
@Table(name = "alertas")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alerta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    // Foreign key to cultivos table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cultivo_id", nullable = false)
    private Cultivo cultivo;
    
    // Foreign key to reglas table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regla_id", nullable = false)
    private Regla regla;
    
    @Column(nullable = false)
    private Boolean leida;
    
    @Column(name = "fecha_generacion")
    private LocalDateTime fechaGeneracion;
    
    @Column(name = "fecha_vencimiento")
    private LocalDateTime fechaVencimiento;
    
    // Automatically set generation date and read status before saving
    @PrePersist
    protected void onCreate() {
        fechaGeneracion = LocalDateTime.now();
        if (leida == null) {
            leida = false;
        }
    }
}
