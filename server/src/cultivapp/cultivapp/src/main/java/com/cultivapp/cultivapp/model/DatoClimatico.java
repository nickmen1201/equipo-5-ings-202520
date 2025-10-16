package com.cultivapp.cultivapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

// DatoClimatico entity: represents climate data for a specific date
@Entity
@Table(name = "datos_climaticos")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DatoClimatico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private LocalDate fecha;
    
    @Column(name = "temperatura_min", precision = 4, scale = 1)
    private BigDecimal temperaturaMin;
    
    @Column(name = "temperatura_max", precision = 4, scale = 1)
    private BigDecimal temperaturaMax;
    
    @Column(name = "precipitacion_mm", precision = 6, scale = 2)
    private BigDecimal precipitacionMm;
    
    @Column(name = "humedad_relativa", precision = 5, scale = 2)
    private BigDecimal humedadRelativa;
    
    @Column(name = "velocidad_viento_kmh", precision = 5, scale = 2)
    private BigDecimal velocidadVientoKmh;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    // Automatically update modification date before updating
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
