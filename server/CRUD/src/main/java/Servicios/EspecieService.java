package Servicios;

import Clases.Especie;

import java.util.ArrayList;
import java.util.List;

public class EspecieService {
    private static List<Especie> especies = new ArrayList<>();

    public enum TipoEspecie{Ortaliza, Tuberculo, Fruta}
    public enum Ciclo{Corto, Intermedio, Largo, Perenne}
    public enum Suelos{Arenoso, Limoso, Arcilloso, Franco}

    public void AgregarEspecie(Especie especie){
        especies.add(especie);
    }
}
