package com.cultivapp.cultivapp.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "tareas")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // --- Estado de la tarea ---
    @Column(nullable = false)
    private boolean activa;

    @Column(nullable = false)
    private boolean realizada;

    @Column(nullable = false)
    private boolean vencida;

    // --- Fechas clave ---
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_programada", nullable = false)
    private LocalDateTime fechaProgramada; // cuándo debe ejecutarse

    @Column(name = "fecha_realizacion")
    private LocalDateTime fechaRealizacion; // cuándo se realizó (si aplica)

    @Column(name = "fecha_vencimiento")
    private LocalDateTime fechaVencimiento; // hasta cuándo tiene validez

    // --- Relación con la regla ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regla_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "tareas"})
    private Regla regla;

    // --- Relación con el cultivo ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cultivo_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "tareas"})
    private Cultivo cultivo;

    
    @PrePersist
    protected void onCreate() {
        activa=true;
        realizada=false;
        vencida=false;
        fechaCreacion = LocalDateTime.now();

        // Si no se define la fecha programada, la tarea se programa para hoy
        if (fechaProgramada == null) {
            fechaProgramada = LocalDateTime.now();
        }

        // Calcular vencimiento automático si la regla tiene intervalo
        if (regla != null && regla.getIntervaloDias() != null) {
            fechaVencimiento = fechaProgramada.plusDays(regla.getIntervaloDias());
        }
    }
}