package Clases;

import Servicios.UsuarioServicio;

public class Regla {
    private int id;
    private String descripcion;
    private String tipoRegla;
    private String accionRecomendada;
    private String observaciones;

    private static int ides = 0;

    public Regla(String descripcion, String tipoRegla, String accionRecomendada, String observaciones) {
        id = ides;
        this.descripcion = descripcion;
        this.tipoRegla = tipoRegla;
        this.accionRecomendada = accionRecomendada;
        this.observaciones = observaciones;
        ides ++;

    }

    //Getters
    public String getDescripcion() {
        return descripcion;
    }

    public String getTipoRegla() {
        return tipoRegla;
    }

    public String getAccionRecomendada() {
        return accionRecomendada;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public static int getIdes() {
        return ides;
    }

    //Setters
    //Cada setter va a revisar que el modificador sea un administrador
    public void setTipoRegla(String tipoRegla, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.tipoRegla = tipoRegla;
        }
    }

    public void setAccionRecomendada(String accionRecomendada, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.accionRecomendada = accionRecomendada;
        }
    }

    public void setObservaciones(String observaciones, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.observaciones = observaciones;
        }
    }

    public void setDescripcion(String descripcion, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.descripcion = descripcion;
        }
    }

}
