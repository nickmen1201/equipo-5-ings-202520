package com.cultivapp.cultivapp.services;

import com.cultivapp.cultivapp.Model.Clases.Especie;
import com.cultivapp.cultivapp.Model.Util.JsonManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service  // ← Esta anotación le dice a Spring que es un servicio
public class EspecieService {

    private List<Especie> especies = new ArrayList<>();
    private JsonManager<List<Especie>> converter = new JsonManager<>("src/main/java/com/cultivapp/cultivapp/Datos/especies.json");
    private JsonManager<Especie> converterr = new JsonManager<>("src/main/java/com/cultivapp/cultivapp/Datos/especies.json");

    // Constructor que carga las especies al iniciar
    public EspecieService() {
        CargarEspecies();
    }

    // Métodos
    public void AgregarEspecie(Especie especie) {
        especies.add(especie);
    }

    public Especie ObtenerEspeciePorId(int id) {
        return especies.stream()
                .filter(espec -> espec.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Especie> ObtenerTodasLasEspecies() {
        return especies;
    }

    public void ActualizarEspecie(Especie especie) {
        Especie especieVieja = ObtenerEspeciePorId(especie.getId());
        if (especieVieja != null) {
            especies.remove(especieVieja);
            especies.add(especie);
        }
    }

    public void GuardarCambios() {
        try {
            converter.serializar(especies);
            CargarEspecies();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CargarEspecies() {
        try {
            especies = converterr.deserializarLista(Especie.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



   