package com.comite.olimpico;

import java.io.*;
import java.time.LocalDate;
import java.lang.reflect.Type;
import java.util.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class GestorAtletas {
    private List<Atleta> atletas = new ArrayList<>();
    private Gson gson;

    public GestorAtletas() {
        gson = new GsonBuilder()
                // adapter para LocalDate (serializar como "YYYY-MM-DD")
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
                        new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, context) ->
                        LocalDate.parse(json.getAsString()))
                .setPrettyPrinting()
                .create();
    }

    public void registrarAtleta(Atleta a) { atletas.add(a); }

    public Atleta buscarAtleta(String nombre) {
        for (Atleta a : atletas) {
            if (a.getNombre().equalsIgnoreCase(nombre)) return a;
        }
        return null;
    }

    public void mostrarHistorial(String nombre) {
        Atleta a = buscarAtleta(nombre);
        if (a == null) { System.out.println("Atleta no encontrado."); return; }
        System.out.println("Historial de " + a);
        a.obtenerEvolucion().forEach(System.out::println);
        System.out.printf("Promedio: %.3f\n", a.obtenerPromedio());
        System.out.printf("Mejor marca: %.3f\n", a.obtenerMejorMarca());
    }

    public void guardarDatosJSON(String ruta) throws IOException {
        try (Writer w = new FileWriter(ruta)) {
            gson.toJson(atletas, w);
        }
    }

    public void cargarDatosJSON(String ruta) throws IOException {
        try (Reader r = new FileReader(ruta)) {
            Type listType = new TypeToken<List<Atleta>>(){}.getType();
            List<Atleta> cargados = gson.fromJson(r, listType);
            if (cargados != null) atletas = cargados;
        }
    }
}
