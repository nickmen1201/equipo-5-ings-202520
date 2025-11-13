package com.cultivapp.cultivapp.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.cultivapp.cultivapp.models.enums.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Cultivo entity: represents a farmer's crop
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

    @Column(name = "fecha_inicio_etapa")
    private LocalDateTime fechaInicioEtapa;
    
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
    
    
    @Column(name = "etapa_actual")
    private Short  etapaActual;
    
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

    @Column(name = "salud_riego")
    private double saludRiego;

    @Column(name = "salud_fertilizacion")
    private double saludFertilizacion;

    @Column(name = "salud_mantenimiento")
    private double saludMantenimiento;
    
    @OneToMany(mappedBy = "cultivo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"cultivo"})
    @Builder.Default
    private List<Tarea> tareas = new ArrayList<>();


    // Automatically set creation date before saving
    @PrePersist
    protected void onCreate() {
        saludRiego=75.0;
        saludFertilizacion=75.0;
        saludMantenimiento=75.0;
        fechaInicioEtapa = LocalDateTime.now();
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
        etapaActual=1;
    }
    
    // Automatically update modification date before updating
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}





