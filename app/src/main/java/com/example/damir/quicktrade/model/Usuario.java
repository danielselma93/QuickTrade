package com.example.damir.quicktrade.model;

/**
 * Created by Damir on 13/01/2018.
 */

public class Usuario {

    String usuario, email, nombre, apellidos, direccion;

    public Usuario() {

    }

    public Usuario(String usuario, String email, String nombre, String apellidos, String direccion) {
        this.usuario = usuario;
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
