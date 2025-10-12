package com.cultivapp.cultivapp;

import com.cultivapp.cultivapp.Model.Clases.Especie;
import com.cultivapp.cultivapp.Model.Clases.Etapa;
import com.cultivapp.cultivapp.Model.Clases.Regla;
import com.cultivapp.cultivapp.Model.Util.EspecieManager;
import com.cultivapp.cultivapp.Model.Util.JsonManager;
import com.cultivapp.cultivapp.Model.Util.ReglaManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CultivAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CultivAppApplication.class, args);
	}

    @Bean
    public CommandLineRunner runTests() {
        return args -> {
            System.out.println("=== Pruebas en consola ===");

            //Inicializacion de especies
            EspecieManager.CargarEspecies();

//            JsonManager<Especie> converter = new JsonManager<>("src/main/java/com/cultivapp/cultivapp/Datos/especie.json");
//
//            //Instanciacion de especies:
//            List<Regla> reglas = new ArrayList<>();
//            List<Etapa> etapas = new ArrayList<>();
//
//
//
//            Especie especiePrueba = new Especie(etapas, "NombreComun especie", "nombreCientifico", EspecieManager.TipoEspecie.Tuberculo, EspecieManager.Ciclo.Corto, EspecieManager.Suelos.Arenoso, 5.5f, 5.4f, 1000, 32, 28, 32, 42, 9000);
//
//            Etapa etapaPrueba = new Etapa(reglas, "Etapa de prueba", "Esta es una etapa de prueba para ver el funcionamiento del json", 20, "Estas son las observaciones de la etapa de prueba", "Esto es el tipo de riego, deberia ser un enum", "Esta es la fertilizacion, agregar texto");
//            etapas.add(etapaPrueba);
//
//            Regla reglaPrueba = new Regla("Esta es una regla de prueba", ReglaManager.TipoRegla.alta, "Se recomienda tener en cuenta que esto solo es de prueba", "Estas son las observaciones");
//            reglas.add(reglaPrueba);
//
//            try {
//                String resultado = converter.serializar(especiePrueba);
//                System.out.println(resultado);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            System.out.println("=== Fin Pruebas en consola ===");
        };
    }

}
