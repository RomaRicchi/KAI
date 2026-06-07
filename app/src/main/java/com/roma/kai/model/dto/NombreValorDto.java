package com.roma.kai.model.dto;

public class NombreValorDto {
    private String nombre;
    private int valor;

    public NombreValorDto(String nombre, int valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public NombreValorDto() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
