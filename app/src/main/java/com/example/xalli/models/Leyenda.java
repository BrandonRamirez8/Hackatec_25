package com.example.xalli.models;

import com.google.gson.annotations.SerializedName;

public class Leyenda {
    @SerializedName("id_leyenda")
    private int id;
    @SerializedName("Nombre")
    private String nombre;
    @SerializedName("Historia")
    private String historia;
    @SerializedName("Imágenes")
    private String imagenes;
    @SerializedName("ID_Pueblo")
    private int idPueblo;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getHistoria() {
        return historia;
    }

    public String getImagenes() {
        return imagenes;
    }

    public int getIdPueblo() {
        return idPueblo;
    }

    @Override
    public String toString() {
        return "Leyenda: " + nombre + " (Pueblo ID: " + idPueblo + ") - " + historia.substring(0, Math.min(historia.length(), 50)) + (historia.length() > 50 ? "..." : "");
    }
}
