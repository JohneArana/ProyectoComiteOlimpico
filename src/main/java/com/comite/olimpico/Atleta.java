package com.comite.olimpico;

import java.util.*;
import java.util.stream.Collectors;

public class Atleta {
    private String nombre;
    private int edad;
    private String disciplina;
    private String departamento;
    private List<Entrenamiento> entrenamientos = new ArrayList<>();

    public Atleta() {}

    public Atleta(String nombre, int edad, String disciplina, String departamento) {
        this.nombre = nombre;
        this.edad = edad;
        this.disciplina = disciplina;
        this.departamento = departamento;
    }

    public String getNombre() { return nombre; }
    public String getDisciplina() { return disciplina; }
    public List<Entrenamiento> getEntrenamientos() { return entrenamientos; }

    public void agregarEntrenamiento(Entrenamiento e) {
        entrenamientos.add(e);
    }

    public double obtenerPromedio() {
        return entrenamientos.stream().mapToDouble(Entrenamiento::getMarca).average().orElse(0);
    }

    public double obtenerMejorMarca() {
        if (entrenamientos.isEmpty()) return 0;
        // Heurística: si parece ser una medida de tiempo (velocidad), 'mejor' = menor.
        boolean mejorEsMenor = entrenamientos.stream().anyMatch(e -> {
            String t = e.getTipo().toLowerCase();
            return t.contains("tiempo") || t.contains("segundo") || t.contains("velocidad") || t.contains("100m");
        }) || (disciplina != null && disciplina.toLowerCase().contains("atletismo"));

        return mejorEsMenor
                ? entrenamientos.stream().mapToDouble(Entrenamiento::getMarca).min().orElse(0)
                : entrenamientos.stream().mapToDouble(Entrenamiento::getMarca).max().orElse(0);
    }

    public List<Entrenamiento> obtenerEvolucion() {
        return entrenamientos.stream()
                .sorted(Comparator.comparing(Entrenamiento::getFecha))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return nombre + " (" + disciplina + ", " + departamento + ")";
    }
}
