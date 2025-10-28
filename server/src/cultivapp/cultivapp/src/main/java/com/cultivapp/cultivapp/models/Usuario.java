package com.cultivapp.cultivapp.models;


import java.time.LocalDateTime;

import com.cultivapp.cultivapp.models.enums.Roles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Usuario entity: represents a system user (admin or producer)
// Enum stored as string to keep database portable
@Entity
@Table(name = "usuarios")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    
    // Password stored as BCrypt hash, never plain text
    @Column(nullable = false, length = 255)
    private String password;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(nullable = false, length = 100)
    private String apellido;
    
    private String ciudad;
    
    @Column(length = 20)
    private String telefono;
    
    // Enum stored as string for readability and portability
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roles rol;
    
    @Column(nullable = false)
    private Boolean activo;
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    
    // Automatically set registration date before saving
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }
}