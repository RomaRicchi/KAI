package com.roma.kai.model.dto;

import com.google.gson.annotations.SerializedName;

public class KaiStateResponse {
    @SerializedName("estado_actual")
    private String estadoActual;
    @SerializedName("etapa_actual")
    private String etapaActual;
    private int energia;
    @SerializedName("imagen_kai")
    private String imageKai;
    @SerializedName("nivel_vinculo")
    private int nivelVinculo;
    @SerializedName("modo_recuperacion")
    private boolean modoRecuperacion;
    @SerializedName("modo_actual")
    private String modoActual;
    @SerializedName("ultima_evolucion")
    private String ultimaEvolucion;
    @SerializedName("atributo_dominante")
    private String atributoDominante;

    public String getEstadoActual() {
        return estadoActual;
    }

    public String getEtapaActual() {
        return etapaActual;
    }

    public int getEnergia() {
        return energia;
    }

    public String getImageKai() {
        return imageKai;
    }

    public int getNivelVinculo() {
        return nivelVinculo;
    }

    public boolean isModoRecuperacion() {
        return modoRecuperacion;
    }

    public String getModoActual() {
        return modoActual;
    }

    public String getUltimaEvolucion() {
        return ultimaEvolucion;
    }

    public String getAtributoDominante() {
        return atributoDominante;
    }
}
