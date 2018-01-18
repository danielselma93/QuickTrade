package com.example.damir.quicktrade.model;

/**
 * Created by Damir on 14/01/2018.
 */

public class Producto {

    String nombre, descripcion, categoria;
    float precio;

    public Producto() {

    }

    public Producto(String nombre, String descripcion, String categoria, float precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        String str = "Nombre: "+nombre+". Descripcion: "+descripcion+"\nCategoria: "+categoria+". Precio: "+precio;
        return str;
    }
}
