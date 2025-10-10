package Clases;

import Servicios.EspecieService;
import Servicios.UsuarioServicio;

import java.util.List;

public class Especie {
    private int id;
    private List<Etapa> l_etapas;
    //Falta postCosecha
    //Falta DatosProduccion
    private String nombreComun;
    private String nombreCientifico;
    private EspecieService.TipoEspecie tipoEspecie;
    private EspecieService.Ciclo ciclo;
    private EspecieService.Suelos sueloIdeal;
    private float phMax;
    private float phMin;
    private int altitudOptima;
    private int temperaturaOptima;
    private int diasGerminacion;
    private int diasFloracion;
    private int diasCosecha;
    private int aguaSemanalmm;

    private static int ides = 0;

    public Especie(List<Etapa> l_etapas, String nombreComun, String nombreCientifico, EspecieService.TipoEspecie tipoEspecie, EspecieService.Ciclo ciclo, EspecieService.Suelos sueloIdeal, float phMax, float phMin, int altitudOptima, int temperaturaOptima, int diasGerminacion, int diasFloracion, int diasCosecha, int aguaSemanalmm) {
        id = ides;
        this.l_etapas = l_etapas;
        this.nombreComun = nombreComun;
        this.nombreCientifico = nombreCientifico;
        this.tipoEspecie = tipoEspecie;
        this.ciclo = ciclo;
        this.sueloIdeal = sueloIdeal;
        this.phMax = phMax;
        this.phMin = phMin;
        this.altitudOptima = altitudOptima;
        this.temperaturaOptima = temperaturaOptima;
        this.diasGerminacion = diasGerminacion;
        this.diasFloracion = diasFloracion;
        this.diasCosecha = diasCosecha;
        this.aguaSemanalmm = aguaSemanalmm;

        ides++;
    }

    //Getters
    public List<Etapa> getL_etapas() {
        return l_etapas;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public EspecieService.TipoEspecie getTipoEspecie() {
        return tipoEspecie;
    }

    public EspecieService.Ciclo getCiclo() {
        return ciclo;
    }

    public EspecieService.Suelos getSueloIdeal() {
        return sueloIdeal;
    }

    public float getPhMax() {
        return phMax;
    }

    public float getPhMin() {
        return phMin;
    }

    public int getAltitudOptima() {
        return altitudOptima;
    }

    public int getTemperaturaOptima() {
        return temperaturaOptima;
    }

    public int getDiasGerminacion() {
        return diasGerminacion;
    }

    public int getDiasFloracion() {
        return diasFloracion;
    }

    public int getDiasCosecha() {
        return diasCosecha;
    }

    public int getAguaSemanalmm() {
        return aguaSemanalmm;
    }

    //Setters

    public void setL_etapas(List<Etapa> l_etapas, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.l_etapas = l_etapas;
        }

    }

    public void setNombreComun(String nombreComun, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.nombreComun = nombreComun;
        }
    }

    public void setNombreCientifico(String nombreCientifico, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.nombreCientifico = nombreCientifico;
        }
    }

    public void setTipoEspecie(EspecieService.TipoEspecie tipoEspecie, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.tipoEspecie = tipoEspecie;
        }
    }

    public void setCiclo(EspecieService.Ciclo ciclo, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.ciclo = ciclo;
        }
    }

    public void setSueloIdeal(EspecieService.Suelos sueloIdeal, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.sueloIdeal = sueloIdeal;
        }
    }

    public void setPhMax(float phMax, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.phMax = phMax;
        }
    }

    public void setPhMin(float phMin, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.phMin = phMin;
        }
    }

    public void setAltitudOptima(int altitudOptima, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.altitudOptima = altitudOptima;
        }
    }

    public void setTemperaturaOptima(int temperaturaOptima, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.temperaturaOptima = temperaturaOptima;
        }
    }

    public void setDiasGerminacion(int diasGerminacion, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.diasGerminacion = diasGerminacion;
        }
    }

    public void setDiasFloracion(int diasFloracion, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.diasFloracion = diasFloracion;
        }
    }

    public void setDiasCosecha(int diasCosecha, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.diasCosecha = diasCosecha;
        }
    }

    public void setAguaSemanalmm(int aguaSemanalmm, Usuario user) {
        if (user.getRol().equals(UsuarioServicio.Rol.Administrador)) {
            this.aguaSemanalmm = aguaSemanalmm;
        }
    }


}
