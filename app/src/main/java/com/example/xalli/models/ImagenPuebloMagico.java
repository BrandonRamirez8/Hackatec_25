package com.example.xalli.models;

import com.google.gson.annotations.SerializedName;

public class ImagenPuebloMagico {

    @SerializedName("id_imagen")
    private int idImagen;

    @SerializedName("url_imagen")
    private String urlImagen;

    // --- Getters ---

    public int getIdImagen() {
        return idImagen;
    }

    public String getUrlImagen() {
        return urlImagen;
    }
}