package com.roma.kai.model.entity;

public class Categoria {
    private String nombre;
    private int iconResId;

    public Categoria(String nombre, int iconResId) {
        this.nombre = nombre;
        this.iconResId = iconResId;
    }

    public String getNombre() { return nombre; }
    public int getIconResId() { return iconResId; }
}
