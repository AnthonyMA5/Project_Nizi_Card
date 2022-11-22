package com.example.proyecto_tarjeta;

import java.io.Serializable;

public class User implements Serializable {
    int id;
    String nombre, apellidos, correo, telefono, username, contrasena;

    public User() {
    }

    public User(int id, String nombre, String apellidos, String correo, String telefono, String username, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.username = username;
        this.contrasena = contrasena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
