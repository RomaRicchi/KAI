package com.roma.kai.model.entity;

public class Habito {
    private String nombre;
    private String categoria;
    private int xp;
    private int racha;
    private boolean completado;
    private int iconResId; // Para el icono segun la imagen

    public Habito(String nombre, String categoria, int xp, int racha, boolean completado, int iconResId) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.xp = xp;
        this.racha = racha;
        this.completado = completado;
        this.iconResId = iconResId;
    }

    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public int getXp() { return xp; }
    public int getRacha() { return racha; }
    public boolean isCompletado() { return completado; }
    public void setCompletado(boolean completado) { this.completado = completado; }
    public int getIconResId() { return iconResId; }
}
