package com.example.xalli.models;

import com.google.gson.annotations.SerializedName;

public class Restaurante {
    @SerializedName("id_restaurante")
    private int id;
    @SerializedName("Nombre")
    private String nombre;
    @SerializedName("ubicacion")
    private String ubicacion;
    @SerializedName("id_pueblo")
    private double idPueblo;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public double getIdPueblo() {
        return idPueblo;
    }

    @Override
    public String toString() {
        return "Restaurante: " + nombre + " (Pueblo ID: " + idPueblo + ") - Ubicación: " + ubicacion;
    }
}
