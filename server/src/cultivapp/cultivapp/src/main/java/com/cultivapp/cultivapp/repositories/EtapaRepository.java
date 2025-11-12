package com.cultivapp.cultivapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cultivapp.cultivapp.models.Especie;
import com.cultivapp.cultivapp.models.Etapa;



@Repository
public interface EtapaRepository extends JpaRepository<Etapa, Integer> {
    List<Etapa> findByEspecieOrderByOrden(Especie especie);

    @Query("""
    SELECT e FROM Etapa e
    JOIN FETCH e.reglas r
    WHERE e.especie.id = :especieId
    AND e.orden = :orden
    """)
    Optional<Etapa> findEtapaActualConReglas(@Param("especieId") Integer especieId, @Param("orden") Short orden);

    @Query("SELECT e FROM Etapa e WHERE e.especie.id = :especieId AND e.orden = :orden")
    Etapa findByEspecieIdAndOrden(@Param("especieId") Integer especieId, @Param("orden") short orden);

    @Query("SELECT e FROM Etapa e WHERE e.especie.id = :especieId AND e.orden > :orden ORDER BY e.orden ASC LIMIT 1")
    Etapa findNextEtapa(@Param("especieId") Integer especieId, @Param("orden") short orden);
}
