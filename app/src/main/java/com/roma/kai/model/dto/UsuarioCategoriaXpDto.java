package com.roma.kai.model.dto;

import com.google.gson.annotations.SerializedName;

public class UsuarioCategoriaXpDto {
    @SerializedName("categoria_xp_id")
    private String categoriaXpId;
    private String nombre;
    private int valor;

    public String getCategoriaXpId() {
        return categoriaXpId;
    }

    public String getNombre() {
        return nombre;
    }

    public int getValor() {
        return valor;
    }
}
