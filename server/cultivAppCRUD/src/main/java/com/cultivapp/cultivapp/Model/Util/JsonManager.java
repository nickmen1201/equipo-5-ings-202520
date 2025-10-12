package com.cultivapp.cultivapp.Model.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class JsonManager <E>{
    private final Gson gson;
    private final String path;

    public JsonManager(String path) {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.path = path;
    }

    //Serializar un objeto o lista
    public String serializar(E objeto) throws Exception {
        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(objeto, writer);
            return "Serialización exitosa en: " + path;
        } catch (IOException e) {
            throw new Exception("Error al serializar: " + e.getMessage(), e);
        }
    }

    //Deserializar una lista genérica
    public List<E> deserializarLista(Class<E> clase) throws Exception {
        Type tipoLista = TypeToken.getParameterized(List.class, clase).getType();

        try (FileReader reader = new FileReader(path)) {
            return gson.fromJson(reader, tipoLista);
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado, se devuelve lista vacía.");
            return List.of();
        } catch (IOException e) {
            throw new Exception("Error al deserializar: " + e.getMessage(), e);
        }
    }

    //Deserializar un solo objeto
    public E deserializarObjeto(Class<E> clase) throws Exception {
        try (FileReader reader = new FileReader(path)) {
            return gson.fromJson(reader, clase);
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado: " + path);
            return null;
        } catch (IOException e) {
            throw new Exception("Error al deserializar: " + e.getMessage(), e);
        }
    }
}
