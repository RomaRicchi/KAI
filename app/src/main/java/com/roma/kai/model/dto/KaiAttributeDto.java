package com.roma.kai.model.dto;

public class KaiAttributeDto {
    private String atributo;
    private int valor;

    public KaiAttributeDto(String atributo, int valor) {
        this.atributo = atributo;
        this.valor = valor;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
