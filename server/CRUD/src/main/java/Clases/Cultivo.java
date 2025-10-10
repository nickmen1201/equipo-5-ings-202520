package Clases;

import Servicios.CultivoService;
import Servicios.EspecieService;

import java.util.Date;

public class Cultivo {

    private Especie especie;
    private Date fechaInicio;
    private EspecieService.Suelos suelo;
    private float ph;
    private int altitud;
    private float temperatura;
    private float areaHectarias;
    private CultivoService.Estados estado;
    private float rendimientoKg;

    private static int ides = 0;


    public Cultivo(Especie especie, Date fechaInicio, int id, EspecieService.Suelos suelo, float ph, int altitud, float temperatura, float areaHectarias, CultivoService.Estados estado, float rendimientoKg) {
        id = ides;
        this.especie = especie;
        this.fechaInicio = fechaInicio;
        this.id = id;
        this.suelo = suelo;
        this.ph = ph;
        this.altitud = altitud;
        this.temperatura = temperatura;
        this.areaHectarias = areaHectarias;
        this.estado = estado;
        this.rendimientoKg = rendimientoKg;

        ides ++;
    }

    //Getters
    private int id;

    public int getId() {
        return id;
    }

    public Especie getEspecie() {
        return especie;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public EspecieService.Suelos getSuelo() {
        return suelo;
    }

    public float getPh() {
        return ph;
    }

    public int getAltitud() {
        return altitud;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public float getAreaHectarias() {
        return areaHectarias;
    }

    public CultivoService.Estados getEstado() {
        return estado;
    }

    public float getRendimientoKg() {
        return rendimientoKg;
    }

    //Setters
    public void setEspecie(Especie especie) {

        this.especie = especie;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setSuelo(EspecieService.Suelos suelo) {
        this.suelo = suelo;
    }

    public void setPh(float ph) {
        this.ph = ph;
    }

    public void setAltitud(int altitud) {
        this.altitud = altitud;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public void setAreaHectarias(float areaHectarias) {
        this.areaHectarias = areaHectarias;
    }

    public void setEstado(CultivoService.Estados estado) {
        this.estado = estado;
    }

    public void setRendimientoKg(float rendimientoKg) {
        this.rendimientoKg = rendimientoKg;
    }


}
