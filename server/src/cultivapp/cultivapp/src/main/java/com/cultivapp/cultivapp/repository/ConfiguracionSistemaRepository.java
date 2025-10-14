package com.cultivapp.cultivapp.repository;

import com.cultivapp.cultivapp.model.ConfiguracionSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository for ConfiguracionSistema entity: provides database access methods
@Repository
public interface ConfiguracionSistemaRepository extends JpaRepository<ConfiguracionSistema, Integer> {
}
