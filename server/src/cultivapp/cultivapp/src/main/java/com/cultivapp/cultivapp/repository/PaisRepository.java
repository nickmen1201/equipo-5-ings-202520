package com.cultivapp.cultivapp.repository;

import com.cultivapp.cultivapp.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository for Pais entity: provides database access methods
@Repository
public interface PaisRepository extends JpaRepository<Pais, Integer> {
}
