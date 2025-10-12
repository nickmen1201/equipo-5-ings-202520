package com.cultivapp.cultivapp.Model.Clases;


import com.cultivapp.cultivapp.Model.Util.ReglaManager;
import com.cultivapp.cultivapp.Model.Util.UsuarioManager;

public class Regla {

    private int id;
    private String descripcion;
    private String accionRecomendada;
    private String observaciones;
    private ReglaManager.TipoRegla tipoRegla;


    private static int ides = 0;

    public Regla(String descripcion, ReglaManager.TipoRegla tipoRegla, String accionRecomendada, String observaciones) {
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

    public ReglaManager.TipoRegla getTipoRegla() {
        return tipoRegla;
    }

    public String getAccionRecomendada() {
        return accionRecomendada;
    }

    public String getObservaciones() {
        return observaciones;
    }


    //Cada setter va a revisar que el modificador sea un administrador
    //Setters
    public void setTipoRegla(ReglaManager.TipoRegla tipoRegla, Usuario user) {

        if (user.getRol().equals(UsuarioManager.Rol.Administrador)) {
            this.tipoRegla = tipoRegla;
        }
    }

    public void setAccionRecomendada(String accionRecomendada, Usuario user) {
        if (user.getRol().equals(UsuarioManager.Rol.Administrador)) {
            this.accionRecomendada = accionRecomendada;
        }
    }

    public void setObservaciones(String observaciones, Usuario user) {
        if (user.getRol().equals(UsuarioManager.Rol.Administrador)) {
            this.observaciones = observaciones;
        }
    }

    public void setDescripcion(String descripcion, Usuario user) {
        if (user.getRol().equals(UsuarioManager.Rol.Administrador)) {
            this.descripcion = descripcion;
        }
    }
}
