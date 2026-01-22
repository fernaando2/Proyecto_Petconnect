package com.example.proyecto_petconnect;

public class Mascota {
    private String nombre;
    private String especie;
    private String descripcion;
    private String estado; // "Perdido" o "Avistado"

    public Mascota(String nombre, String especie, String descripcion, String estado) {
        this.nombre = nombre;
        this.especie = especie;
        this.descripcion = descripcion;
        this.estado = estado;
    }
    // Getters y Setters (puedes generarlos con Alt+Insert)
}