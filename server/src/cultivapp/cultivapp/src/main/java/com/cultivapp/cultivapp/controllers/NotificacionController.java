package com.cultivapp.cultivapp.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cultivapp.cultivapp.models.Notificacion;
import com.cultivapp.cultivapp.services.NotificacionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;

    
    @GetMapping("/{id}")
    public List<Notificacion> getNotificaciones(@PathVariable Integer id){
       return notificacionService.getUserNotifications(id);
    }

    @GetMapping("/{id}/leido")
    public void  toogleLeido(@PathVariable Integer id) {
        notificacionService.toggleLeido(id);
    }

    @DeleteMapping("/{id}")
    public void deleteNotificacion(@PathVariable Integer id) {
        notificacionService.deleteNotificacion(id);
    }

    @DeleteMapping("/usuario/{id}")
    public void deleteAllNotificacionesUsuario(@PathVariable Integer id) {
        notificacionService.deleteAllByUsuario(id);
    }

    @GetMapping("/usuario/leido/{id}")
    public void  toogleAllLeido(@PathVariable Integer id) {
        notificacionService.toogleAllLeidas(id);
    }

}
