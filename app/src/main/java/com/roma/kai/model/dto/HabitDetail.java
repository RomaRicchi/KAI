package com.roma.kai.model.dto;

import com.google.gson.annotations.SerializedName;

public class HabitDetail {
    @SerializedName("habito_usuario_id")
    private String id;
    private String nombre;
    private String categoria;
    private String descripcion;
    @SerializedName("xp_total")
    private int xpTotal;
    @SerializedName("racha_actual")
    private int rachaActual;
    @SerializedName("imagen_habito")
    private String imagenHabito;

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public String getDescripcion() { return descripcion; }
    public int getXpTotal() { return xpTotal; }
    public int getRachaActual() { return rachaActual; }
    public String getImagenHabito() { return imagenHabito; }
}
