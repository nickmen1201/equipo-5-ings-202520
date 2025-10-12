package com.cultivapp.cultivapp.Model.Util;

import com.cultivapp.cultivapp.Model.Clases.Cultivo;
import com.cultivapp.cultivapp.Model.Clases.Especie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EspecieManager {

    private static List<Especie> especies = new ArrayList<>();

    private static JsonManager <List<Especie>> converter = new JsonManager<>("src/main/java/com/cultivapp/cultivapp/Datos/especies.json");
    private static JsonManager<Especie> converterr = new JsonManager<>("src/main/java/com/cultivapp/cultivapp/Datos/especies.json"); //Revisar

    public static enum TipoEspecie{Ortaliza, Tuberculo, Fruta}
    public static enum Ciclo{Corto, Intermedio, Largo, Perenne}
    public static enum Suelos{Arenoso, Limoso, Arcilloso, Franco}


    //Metodos
    public static void AgregarEspecie(Especie especie){
        especies.add(especie);
    }

    public static Especie ObtenerEspeciePorId(int id){
        Especie especie = especies.stream().filter(espec -> espec.getId() == id).findFirst().orElse(null);
        return especie;
    }

    //Revisar
    public static void CambiarEspecie(Especie especie){
        Especie especieVieja = ObtenerEspeciePorId(especie.getId());
        especies.remove(especieVieja);
        especies.add(especie);
    }

    public static void ActualizarEspecies(){
        try {
            converter.serializar(especies);
            CargarEspecies();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void CargarEspecies(){
        try {
            especies = converterr.deserializarLista(Especie.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    //Getters
    public static List<Especie> getEspecies() {
        return especies;
    }



}
