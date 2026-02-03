package com.example.proyecto_petconnect;

public class Mensaje {
    public String usuario;
    public String texto;
    public long timestamp;

    public Mensaje() {} // Obligatorio para Firebase

    public Mensaje(String usuario, String texto, long timestamp) {
        this.usuario = usuario;
        this.texto = texto;
        this.timestamp = timestamp;
    }
}