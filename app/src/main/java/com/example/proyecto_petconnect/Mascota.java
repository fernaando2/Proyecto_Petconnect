package com.example.proyecto_petconnect;

import java.io.Serializable;

public class Mascota implements Serializable {

    private String id;
    private String nombre;
    private String especie;
    private String descripcion;
    private String estado; // <--- Añadido para el Hito 2

    // 1. Constructor vacío (Obligatorio para Firebase)
    public Mascota() {
    }

    // 2. Constructor completo (Actualizado con estado)
    public Mascota(String nombre, String especie, String descripcion, String estado) {
        this.nombre = nombre;
        this.especie = especie;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    // 3. Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEspecie() { return especie; }
    public String getDescripcion() { return descripcion; }
    public String getEstado() { return estado; } // <--- Necesario para el Adapter

    // 4. Setters
    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEspecie(String especie) { this.especie = especie; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Mascota{" +
                "nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}