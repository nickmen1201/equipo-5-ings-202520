package com.cultivapp.cultivapp.repository;

import com.cultivapp.cultivapp.model.Etapa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository for Etapa entity: provides database access methods
@Repository
public interface EtapaRepository extends JpaRepository<Etapa, Integer> {
}
