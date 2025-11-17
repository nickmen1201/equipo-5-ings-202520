package com.cultivapp.cultivapp.services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.cultivapp.cultivapp.models.Notificacion;
import com.cultivapp.cultivapp.models.Usuario;
import com.cultivapp.cultivapp.repositories.NotificacionRepository;
import com.cultivapp.cultivapp.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Notificacion createNotificacion(String mensaje, Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(mensaje);
        notificacion.setUsuario(usuario);

        usuario.getNotificaciones().add(notificacion);
     

        return notificacionRepository.save(notificacion);
    }

    @Transactional
public void createNotificacionParaTodos(String mensaje) {
    List<Usuario> usuarios = usuarioRepository.findAll();

    List<Notificacion> notificaciones = usuarios.stream()
        .map(usuario -> {
            Notificacion n = new Notificacion();
            n.setMensaje(mensaje);
            n.setUsuario(usuario);
            n.setLeida(false);
            return n;
        })
        .toList();

    notificacionRepository.saveAll(notificaciones);
}

    public List<Notificacion> getUserNotifications(Integer usuarioId) {
        return notificacionRepository.findByUsuarioId(usuarioId);
    }

    public void toggleLeido(Integer notificacionId) {
        Notificacion notificacion = notificacionRepository.findById(notificacionId)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));

        notificacion.setLeida(true);
        notificacionRepository.save(notificacion);
    }

    public void deleteNotificacion(Integer notificacionId) {
        notificacionRepository.deleteById(notificacionId);
    }

    @Transactional
    public void deleteAllByUsuario(Integer usuarioId) {
        notificacionRepository.deleteByUsuarioId(usuarioId);
    }

    
    @Transactional
    public void toogleAllLeidas(Integer usuarioId) {
        List<Notificacion> notificaciones = notificacionRepository.findByUsuarioId(usuarioId);

        notificaciones.stream()
                .filter(n -> !n.isLeida()) // Solo las no leídas
                .forEach(n -> n.setLeida(true));

        notificacionRepository.saveAll(notificaciones);
    }
}