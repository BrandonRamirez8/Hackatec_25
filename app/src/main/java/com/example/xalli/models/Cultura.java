package com.example.xalli.models;

import com.google.gson.annotations.SerializedName;

public class Cultura {
    @SerializedName("id_cultura")
    private int id;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Cultura ID: " + id; // Puedes expandir esto si Cultura tiene más campos que mostrar
    }
}
