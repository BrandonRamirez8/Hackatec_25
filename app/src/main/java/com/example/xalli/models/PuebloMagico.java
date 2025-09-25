package com.example.xalli.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PuebloMagico {

    // Los nombres en @SerializedName deben coincidir EXACTAMENTE con los de la tabla
    @SerializedName("id_pueblo")
    private int id;

    @SerializedName("Nombre")
    private String nombre;

    @SerializedName("Descripcion")
    private String descripcion;

    // Esta es la clave: una lista para contener las imágenes anidadas.
    // El nombre "Imagenes_Pueblos_Magicos" debe coincidir con el nombre de la tabla de imágenes.
    @SerializedName("Imagenes_Pueblos_Magicos")
    private List<ImagenPuebloMagico> imagenes;

    // --- Getters ---

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<ImagenPuebloMagico> getImagenes() {
        return imagenes;
    }
}