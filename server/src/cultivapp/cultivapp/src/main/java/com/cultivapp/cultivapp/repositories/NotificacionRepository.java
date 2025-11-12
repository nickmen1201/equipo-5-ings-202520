package com.cultivapp.cultivapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cultivapp.cultivapp.models.Notificacion;



@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
      List<Notificacion> findByUsuarioId(Integer usuarioId);

      void deleteByUsuarioId(Integer usuarioId);
}

