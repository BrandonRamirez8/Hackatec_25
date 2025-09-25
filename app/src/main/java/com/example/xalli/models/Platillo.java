package com.example.xalli.models;

import com.google.gson.annotations.SerializedName;

public class Platillo {
    @SerializedName("id_platillo")
    private int id;
    @SerializedName("Pueblo_id")
    private int puebloId;
    @SerializedName("Nombre")
    private String nombre;
    @SerializedName("Descripción")
    private String descripcion;

    public int getId() {
        return id;
    }

    public int getPuebloId() {
        return puebloId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "Platillo: " + nombre + " (Pueblo ID: " + puebloId + ") - " + descripcion;
    }
}
