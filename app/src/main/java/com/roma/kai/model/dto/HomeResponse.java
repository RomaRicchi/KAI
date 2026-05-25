package com.roma.kai.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeResponse {
    @SerializedName("estado_kai")
    private KaiHomeSummary estadoKai;
    @SerializedName("mensaje_motivacional")
    private String mensajeMotivacional;
    @SerializedName("xp_total")
    private int xpTotal;
    @SerializedName("racha_actual")
    private int rachaActual;
    @SerializedName("habitos_diarios")
    private List<DailyHabitSummary> habitosDiarios;
    @SerializedName("progreso_diario")
    private DailyProgress progresoDiario;

    public KaiHomeSummary getEstadoKai() {
        return estadoKai;
    }

    public String getMensajeMotivacional() {
        return mensajeMotivacional;
    }

    public int getXpTotal() {
        return xpTotal;
    }

    public int getRachaActual() {
        return rachaActual;
    }

    public List<DailyHabitSummary> getHabitosDiarios() {
        return habitosDiarios;
    }

    public DailyProgress getProgresoDiario() {
        return progresoDiario;
    }
}
