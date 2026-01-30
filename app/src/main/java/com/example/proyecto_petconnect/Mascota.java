package com.example.proyecto_petconnect;

import java.io.Serializable;

// Añade "implements Serializable" para que el Intent lo acepte
public class Mascota implements Serializable {
    private String id, nombre, especie, descripcion, estado, fotoPath, usuarioId;

    public Mascota(String nombre, String especie, String descripcion, String estado, String fotoPath, String usuarioId) {
        this.nombre = nombre;
        this.especie = especie;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fotoPath = fotoPath;
        this.usuarioId = usuarioId;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public String getEspecie() { return especie; }
    public String getDescripcion() { return descripcion; }
    public String getEstado() { return estado; }
    public String getFotoPath() { return fotoPath; }
    public String getUsuarioId() { return usuarioId; }
}