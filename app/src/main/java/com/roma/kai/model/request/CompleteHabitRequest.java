package com.roma.kai.model.request;

import com.google.gson.annotations.SerializedName;

public class CompleteHabitRequest {
    @SerializedName("valor_registrado")
    private String valorRegistrado;

    public CompleteHabitRequest(String valorRegistrado) {
        this.valorRegistrado = valorRegistrado;
    }

    public String getValorRegistrado() {
        return valorRegistrado;
    }

    public void setValorRegistrado(String valorRegistrado) {
        this.valorRegistrado = valorRegistrado;
    }
}
