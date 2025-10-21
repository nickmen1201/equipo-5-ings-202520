package com.cultivapp.cultivapp.model;

// Estado enumeration for crop status
// Stored as string in database for portability
public enum Estado {
    ACTIVO,     // Active crop
    COSECHADO,  // Harvested crop
    PERDIDO     // Lost crop (due to disease, weather, etc.)
}
