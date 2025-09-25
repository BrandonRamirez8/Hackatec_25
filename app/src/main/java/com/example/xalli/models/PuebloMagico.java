package com.example.xalli.models;

import com.google.gson.annotations.SerializedName;

public class PuebloMagico {
    @SerializedName("id_pueblo")
    private int id;
    @SerializedName("Nombre")
    private String nombre;
    @SerializedName("Descripción")
    private String descripcion;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "Pueblo Mágico: " + nombre + " - " + descripcion;
    }
}
