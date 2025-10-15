package com.cultivapp.cultivapp.model;

import jakarta.persistence.*;
import lombok.*;

// Etapa entity: represents a crop growth stage for rules
@Entity
@Table(name = "etapas")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Etapa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 30)
    private String nombre;
    
    @Column(length = 200)
    private String descripcion;
}
