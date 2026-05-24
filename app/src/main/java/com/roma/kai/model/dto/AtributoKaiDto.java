package com.roma.kai.model.dto;

import com.google.gson.annotations.SerializedName;

public class AtributoKaiDto {
    @SerializedName("atributo_kai_id")
    private boolean tipoAtributoKaiId;
    private String nombre;
    private int valor;

    public boolean isTipoAtributoKaiId() {
        return tipoAtributoKaiId;
    }

    public String getNombre() {
        return nombre;
    }

    public int getValor() {
        return valor;
    }
}
