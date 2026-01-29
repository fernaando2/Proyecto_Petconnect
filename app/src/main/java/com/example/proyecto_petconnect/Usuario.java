package com.example.proyecto_petconnect;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String id;
    private String nombre;
    private String telefono;
    private String correo;
    private String ciudad;

    public Usuario(String id, String nombre, String telefono, String correo, String ciudad) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.ciudad = ciudad;
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public String getCiudad() { return ciudad; }
}