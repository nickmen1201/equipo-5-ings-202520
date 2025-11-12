package com.cultivapp.cultivapp.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cultivapp.cultivapp.models.Tarea;

@Repository
public interface TareaRepository extends JpaRepository<Tarea,Integer>{
    @Query("SELECT MAX(t.fechaVencimiento) FROM Tarea t WHERE t.cultivo.id = :cultivoId AND t.regla.id = :reglaId")
    LocalDateTime findUltimaFecha(@Param("cultivoId") Integer cultivoId, @Param("reglaId") Integer reglaId);

    List<Tarea> findByCultivoId(Integer cultivoId);
}   