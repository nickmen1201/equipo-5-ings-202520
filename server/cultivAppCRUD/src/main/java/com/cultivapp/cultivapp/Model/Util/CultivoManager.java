package com.cultivapp.cultivapp.Model.Util;

import com.cultivapp.cultivapp.Model.Clases.Cultivo;
import com.cultivapp.cultivapp.Model.Clases.Especie;

import java.util.List;

public class CultivoManager {
    private static List<Cultivo> cultivos;
    public static enum Estados{Activo, Cosechado, Perdido}


    public static List<Cultivo> getCultivos(){
        return cultivos;
    }

    public static Cultivo ObtenerCultivoPorId(int id){
        Cultivo cultivo = cultivos.stream().filter(cult -> cult.getId() == id).findFirst().orElse(null);
        return cultivo;
    }

    public static void AgregarCultivo(Cultivo cultivo){
        cultivos.add(cultivo);
    }

    public static void EliminarCultivo(Cultivo cultivo){
        cultivos.remove(cultivo);
    }



}
