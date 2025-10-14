package com.cultivapp.cultivapp.model;

import jakarta.persistence.*;
import lombok.*;

// Ciudad entity: represents a city within a country
@Entity
@Table(name = "ciudades")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ciudad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    // Foreign key to paises table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pais", nullable = false)
    private Pais pais;
}
