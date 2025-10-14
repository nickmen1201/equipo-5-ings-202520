package com.cultivapp.cultivapp.repository;

import com.cultivapp.cultivapp.model.Especie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository for Especie entity: provides database access methods
@Repository
public interface EspecieRepository extends JpaRepository<Especie, Integer> {
}
