package com.cultivapp.cultivapp.model;

// TipoTarea enumeration for task types
// Stored as string in database for portability
public enum TipoTarea {
    RIEGO,          // Irrigation task
    FERTILIZACION,  // Fertilization task
    PODA,           // Pruning task
    COSECHA,        // Harvest task
    OTRO            // Other tasks
}
