package com.cultivapp.cultivapp.Model.Clases;


import com.cultivapp.cultivapp.Model.Util.CultivoManager;
import com.cultivapp.cultivapp.Model.Util.EspecieManager;

import java.time.LocalDateTime;

public class Cultivo {

    private int id;
    private Usuario sembrador; // se agrego usuario
    private String nombreCultivo;
    private Especie especie;
    private LocalDateTime fechaSiembra;
    private EspecieManager.Suelos suelo; //revisar, si se necesita un manager o com el de especies ese suficiente
    private float ph;// cambiar a especie
    private int altitud;// cambiar a especie
    private float temperatura;// cambiar a especie
    private float areaHectarias;
    private CultivoManager.Estados estado; // revisar
    private Etapa etapaCultivo; // se agrego la etapa
    private float rendimientoKg; // en el mvp no implementamos esto

    private static int ides = 0;

    //Contructor
    public Cultivo(Especie especie, Usuario sembrador, LocalDateTime fechaInicio, int id,
                   EspecieManager.Suelos suelo, float ph, int altitud, float temperatura, float areaHectarias,
                   CultivoManager.Estados estado, float rendimientoKg) {
        id = ides;
        this.especie = especie;
        this.sembrador= sembrador;
        this.fechaSiembra = fechaInicio;
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


    public int getId() {
        return id;
    }

    public Especie getEspecie() {
        return especie;
    }

    public LocalDateTime getFechaSiembra() {
        return fechaSiembra;
    }

    public EspecieManager.Suelos getSuelo() {
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

    public CultivoManager.Estados getEstado() {
        return estado;
    }

    public float getRendimientoKg() {
        return rendimientoKg;
    }


    //los seters no piden comprobacion de rol, porque cada cliente(Campesino) deberia poder modificar sus cultivos
    //Setters
    public void setEspecie(Especie especie) { this.especie = especie; }

    public void setFechaInicio(LocalDateTime fechaSiembra) {
        this.fechaSiembra = fechaSiembra;
    }

    public void setSuelo(EspecieManager.Suelos suelo) {
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

    public void setEstado(CultivoManager.Estados estado) {
        this.estado = estado;
    }

    public void setRendimientoKg(float rendimientoKg) {
        this.rendimientoKg = rendimientoKg;
    }

}
