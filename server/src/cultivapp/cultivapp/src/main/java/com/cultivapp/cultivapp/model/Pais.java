package com.cultivapp.cultivapp.model;

import jakarta.persistence.*;
import lombok.*;

// Pais entity: represents a country
@Entity
@Table(name = "paises")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pais {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
}
