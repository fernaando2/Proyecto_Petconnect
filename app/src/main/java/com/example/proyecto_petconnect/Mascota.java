package com.example.proyecto_petconnect;

import java.io.Serializable;

// Implementamos Serializable para poder pasar este objeto entre pantallas si fuera necesario
public class Mascota implements Serializable {

    private String id; // Útil para identificar mascotas en Firebase o SQLite
    private String nombre;
    private String especie;
    private String descripcion;

    // 1. Constructor vacío (OBLIGATORIO para que Firebase funcione correctamente)
    public Mascota() {
    }

    // 2. Constructor completo
    public Mascota(String nombre, String especie, String descripcion) {
        this.nombre = nombre;
        this.especie = especie;
        this.descripcion = descripcion;
    }

    // 3. Getters (Para que el Adaptador pueda LEER los datos)
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEspecie() { return especie; }
    public String getDescripcion() { return descripcion; }

    // 4. Setters (Para que Firebase o SQLite puedan ESCRIBIR los datos)
    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEspecie(String especie) { this.especie = especie; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    // 5. Método toString (Opcional, muy útil para depurar por consola)
    @Override
    public String toString() {
        return "Mascota{" +
                "nombre='" + nombre + '\'' +
                ", especie='" + especie + '\'' +
                '}';
    }
}