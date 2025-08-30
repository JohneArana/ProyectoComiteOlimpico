package com.comite.olimpico;

import java.time.LocalDate;
import java.util.Scanner;

public class AppMain {
    public static void main(String[] args) {
        GestorAtletas gestor = new GestorAtletas();
        Scanner sc = new Scanner(System.in);
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- Menú Comité Olímpico ---");
            System.out.println("1. Registrar Atleta");
            System.out.println("2. Registrar Entrenamiento");
            System.out.println("3. Mostrar Historial");
            System.out.println("4. Guardar Datos en JSON");
            System.out.println("5. Cargar Datos desde JSON");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            try { opcion = Integer.parseInt(sc.nextLine()); } catch (Exception ex) { opcion = -1; }

            switch (opcion) {
                case 1:
                    System.out.print("Nombre: "); String nombre = sc.nextLine();
                    System.out.print("Edad: "); int edad = Integer.parseInt(sc.nextLine());
                    System.out.print("Disciplina: "); String disciplina = sc.nextLine();
                    System.out.print("Departamento: "); String depto = sc.nextLine();
                    gestor.registrarAtleta(new Atleta(nombre, edad, disciplina, depto));
                    System.out.println("Atleta registrado.");
                    break;
                case 2:
                    System.out.print("Nombre del atleta: ");
                    String n = sc.nextLine();
                    Atleta atleta = gestor.buscarAtleta(n);
                    if (atleta == null) { System.out.println("Atleta no encontrado."); break; }
                    System.out.print("Fecha (YYYY-MM-DD): ");
                    LocalDate fecha = LocalDate.parse(sc.nextLine());
                    System.out.print("Tipo de entrenamiento: ");
                    String tipo = sc.nextLine();
                    System.out.print("Marca (ej.: segundos o kg): ");
                    double marca = Double.parseDouble(sc.nextLine());
                    atleta.agregarEntrenamiento(new Entrenamiento(fecha, tipo, marca));
                    System.out.println("Entrenamiento agregado.");
                    break;
                case 3:
                    System.out.print("Nombre del atleta: ");
                    gestor.mostrarHistorial(sc.nextLine());
                    break;
                case 4:
                    System.out.print("Ruta JSON (por defecto 'atletas.json'): ");
                    String ruta = sc.nextLine().trim();
                    if (ruta.isEmpty()) ruta = "atletas.json";
                    try { gestor.guardarDatosJSON(ruta); System.out.println("Datos guardados en " + ruta); }
                    catch (Exception e) { e.printStackTrace(); }
                    break;
                case 5:
                    System.out.print("Ruta JSON (por defecto 'atletas.json'): ");
                    String ruta2 = sc.nextLine().trim();
                    if (ruta2.isEmpty()) ruta2 = "atletas.json";
                    try { gestor.cargarDatosJSON(ruta2); System.out.println("Datos cargados desde " + ruta2); }
                    catch (Exception e) { e.printStackTrace(); }
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
        sc.close();
    }
}
