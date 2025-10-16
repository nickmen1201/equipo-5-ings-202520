package com.cultivapp.cultivapp.repository;

import com.cultivapp.cultivapp.model.Regla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository for Regla entity: provides database access methods
@Repository
public interface ReglaRepository extends JpaRepository<Regla, Integer> {
}
