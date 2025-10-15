package com.cultivapp.cultivapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

// Tarea entity: represents a task for a specific crop
// Enum stored as string to keep database portable
@Entity
@Table(name = "tareas")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tarea {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    // Foreign key to cultivos table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cultivo_id", nullable = false)
    private Cultivo cultivo;
    
    // Enum stored as string for readability
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTarea tipo;
    
    @Column(nullable = false, length = 300)
    private String descripcion;
    
    @Column(name = "fecha_programada", nullable = false)
    private LocalDate fechaProgramada;
    
    @Column(nullable = false)
    private Boolean completada;
    
    @Column(name = "fecha_completada")
    private LocalDateTime fechaCompletada;
    
    @Column(columnDefinition = "TEXT")
    private String notas;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    // Automatically set creation date before saving
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (completada == null) {
            completada = false;
        }
    }
}
