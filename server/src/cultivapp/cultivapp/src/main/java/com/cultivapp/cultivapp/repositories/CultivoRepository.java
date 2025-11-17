package com.cultivapp.cultivapp.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cultivapp.cultivapp.models.Cultivo;
import com.cultivapp.cultivapp.models.enums.TipoEtapa;

@Repository
public interface CultivoRepository extends JpaRepository<Cultivo, Integer> {
    
    // Find all crops for a specific user
    List<Cultivo> findByUsuarioId(Integer usuarioId);
    
    // Check if any crop exists for a specific species (REQ-005)
    boolean existsByEspecieId(Integer especieId);
    
    // Find all crops for a given species and current stage
    java.util.List<Cultivo> findByEspecieIdAndEtapaActual(Integer especieId, TipoEtapa etapaActual);

    @Query("SELECT c FROM Cultivo c WHERE c.estado = 'ACTIVO'")
    List<Cultivo> findCultivosActivos();

    @Query("""
    SELECT c FROM Cultivo c
    JOIN FETCH c.especie e
    LEFT JOIN FETCH e.etapas et
    LEFT JOIN FETCH et.reglas r
    LEFT JOIN FETCH c.usuario u
    WHERE c.id = :id AND et.orden = c.etapaActual
    """)
    Optional<Cultivo> findCultivoConTodo(@Param("id") Integer cultivoid);


}
