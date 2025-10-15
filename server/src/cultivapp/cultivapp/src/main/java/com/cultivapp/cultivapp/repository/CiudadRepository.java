package com.cultivapp.cultivapp.repository;

import com.cultivapp.cultivapp.model.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository for Ciudad entity: provides database access methods
@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Integer> {
}
