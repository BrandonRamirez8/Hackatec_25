// C:/Users/byrur/Documents/Hjasask/app/src/main/java/com/example/xalli/models/Cultura.java
package com.example.xalli.models;

import com.google.gson.annotations.SerializedName;

public class Cultura {

    @SerializedName("id_cultura")
    private int id;

    // Nombres de columna con mayúsculas deben usar @SerializedName
    @SerializedName("Nombre_Cultura")
    private String nombre;

    @SerializedName("fecha_inicio")
    private String fechaInicio; // Se recibe como String, se puede formatear después

    @SerializedName("fecha_fin")
    private String fechaFin;

    @SerializedName("Descripcion")
    private String descripcion;

    // --- Getters ---

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }
}