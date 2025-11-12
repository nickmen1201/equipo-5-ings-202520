package com.cultivapp.cultivapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cultivapp.cultivapp.models.Regla;

@Repository
public interface ReglaRepository extends JpaRepository<Regla, Integer>, JpaSpecificationExecutor<Regla> {
    // dynamic filtering via Specifications
}

