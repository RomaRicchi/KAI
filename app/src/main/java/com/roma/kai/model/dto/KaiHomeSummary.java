package com.roma.kai.model.dto;

import com.google.gson.annotations.SerializedName;
import com.roma.kai.model.entity.ConfiguracionUsuarioEntity;

public class KaiHomeSummary {
    @SerializedName("estado_actual")
    private String estadoActual;
    @SerializedName("etapa_actual")
    private String etapaActual;
    private int energia;
    @SerializedName("nivel_vinculo")
    private int nivelVinculo;
    @SerializedName("modo_recuperacion")
    private boolean modoRecuperacion;
    @SerializedName("imagen_kai")
    private String imageKai;

    public String getEstadoActual() {
        return estadoActual;
    }

    public String getEtapaActual() {
        return etapaActual;
    }

    public int getEnergia() {
        return energia;
    }

    public int getNivelVinculo() {
        return nivelVinculo;
    }

    public boolean isModoRecuperacion() {
        return modoRecuperacion;
    }

    public String getImageKai() {
        return imageKai;
    }
}
