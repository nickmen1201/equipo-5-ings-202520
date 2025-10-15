package com.cultivapp.cultivapp.repository;

import com.cultivapp.cultivapp.model.DatoClimatico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository for DatoClimatico entity: provides database access methods
@Repository
public interface DatoClimaticoRepository extends JpaRepository<DatoClimatico, Integer> {
}
