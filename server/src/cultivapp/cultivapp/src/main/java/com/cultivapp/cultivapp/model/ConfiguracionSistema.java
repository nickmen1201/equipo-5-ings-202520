package com.cultivapp.cultivapp.model;

import jakarta.persistence.*;
import lombok.*;

// ConfiguracionSistema entity: represents system configuration settings
// Enum stored as string to keep database portable
@Entity
@Table(name = "configuracion_sistema")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfiguracionSistema {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 50)
    private String clave;
    
    @Column(nullable = false, length = 500)
    private String valor;
    
    @Column(length = 200)
    private String descripcion;
    
    // Enum stored as string for readability
    @Enumerated(EnumType.STRING)
    @Column
    private TipoConfiguracion tipo;
    
    @Column(length = 50)
    private String categoria;
}
