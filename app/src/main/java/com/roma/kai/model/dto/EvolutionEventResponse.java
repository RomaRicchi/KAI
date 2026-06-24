package com.roma.kai.model.dto;

import com.google.gson.annotations.SerializedName;

public class EvolutionEventResponse {
    private boolean activo;
    private String etapa;
    @SerializedName("iniciado_en")
    private String iniciadoEn;
    @SerializedName("expira_en")
    private String expiraEn;

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public String getEtapa() { return etapa; }
    public void setEtapa(String etapa) { this.etapa = etapa; }
    public String getIniciadoEn() { return iniciadoEn; }
    public void setIniciadoEn(String iniciadoEn) { this.iniciadoEn = iniciadoEn; }
    public String getExpiraEn() { return expiraEn; }
    public void setExpiraEn(String expiraEn) { this.expiraEn = expiraEn; }
}
