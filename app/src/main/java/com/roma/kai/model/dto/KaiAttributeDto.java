package com.roma.kai.model.dto;

import com.google.gson.annotations.SerializedName;

public class KaiAttributeDto {
    private String nombre;
    private int xp;

    public KaiAttributeDto(String nombre, int xp) {
        this.nombre = nombre;
        this.xp = xp;
    }

    public String getNombre() {
        return nombre;
    }

    public int getXp() {
        return xp;
    }
}
