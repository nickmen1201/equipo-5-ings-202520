package Clases;

import Servicios.UsuarioServicio;

import java.util.List;

public class Etapa {

    private List<Regla> l_reglas;
    private String nombreEtapa;
    private String descripcionEtapa;
    private int duracionDias;
    private String observaciones;
    private String riesgo;
    private String fertilizacion;

    public Etapa(List<Regla> reglas, String nombreEtapa, String descripcionEtapa, int duracionDias, String observaciones, String riesgo, String fertilizacion ) {
        this.l_reglas = reglas;
        this.nombreEtapa = nombreEtapa;
        this.descripcionEtapa = descripcionEtapa;
        this.duracionDias = duracionDias;
        this.observaciones = observaciones;
        this.riesgo = riesgo;
        this.fertilizacion = fertilizacion;

    }

    //Getters
    public List<Regla> getL_reglas() {
        return l_reglas;
    }

    public String getNombreEtapa() {
        return nombreEtapa;
    }

    public String getDescripcionEtapa() {
        return descripcionEtapa;
    }

    public int getDuracionDias() {
        return duracionDias;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public String getRiesgo() {
        return riesgo;
    }

    public String getFertilizacion() {
        return fertilizacion;
    }

    //Setters
    public void setL_reglas(List<Regla> l_reglas, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.l_reglas = l_reglas;
        }

    }

    public void setNombreEtapa(String nombreEtapa, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.nombreEtapa = nombreEtapa;
        }
    }

    public void setDescripcionEtapa(String descripcionEtapa, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.descripcionEtapa = descripcionEtapa;
        }
    }

    public void setDuracionDias(int duracionDias, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.duracionDias = duracionDias;
        }
    }

    public void setObservaciones(String observaciones, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.observaciones = observaciones;
        }
    }

    public void setRiesgo(String riesgo, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.riesgo = riesgo;
        }
    }

    public void setFertilizacion(String fertilizacion, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.fertilizacion = fertilizacion;
        }
    }
}
