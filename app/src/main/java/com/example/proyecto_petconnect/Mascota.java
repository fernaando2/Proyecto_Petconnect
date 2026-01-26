package com.example.proyecto_petconnect;

public class Mascota {
    private String nombre;
    private String especie;
    private String descripcion;

    public Mascota(String nombre, String especie, String descripcion) {
        this.nombre = nombre;
        this.especie = especie;
        this.descripcion = descripcion;
    }

    // Estos métodos son los que te dan error en rojo ahora mismo:
    public String getNombre() { return nombre; }
    public String getEspecie() { return especie; }
    public String getDescripcion() { return descripcion; }
}