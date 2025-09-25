package com.example.xalli;

public class Evento {
    private String nombreCultura;
    private String fechaInicio;
    private String fechaFin;
    private String descripcion;

    public Evento(String nombreCultura, String fechaInicio, String fechaFin, String descripcion) {
        this.nombreCultura = nombreCultura;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
    }

    public String getNombreCultura() { return nombreCultura; }
    public String getFechaInicio() { return fechaInicio; }
    public String getFechaFin() { return fechaFin; }
    public String getDescripcion() { return descripcion; }
}