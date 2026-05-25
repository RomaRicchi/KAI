package com.roma.kai.ui.inicio;

import com.roma.kai.model.dto.DailyHabitSummary;
import com.roma.kai.model.dto.DailyProgress;
import com.roma.kai.model.dto.KaiHomeSummary;

import java.util.List;

public class InicioUiState {
    private final boolean loading;
    private final boolean success;
    private final KaiHomeSummary estadoKai;
    private final String mensajeMotivacional;
    private final int xpTotal;
    private final int rachaActual;
    private final List<DailyHabitSummary> habitosDiarios;
    private final DailyProgress progresoDiario;

    public InicioUiState(boolean loading, boolean success, KaiHomeSummary estadoKai, String mensajeMotivacional, int xpTotal, int rachaActual, List<DailyHabitSummary> habitosDiarios, DailyProgress progresoDiario) {
        this.loading = loading;
        this.success = success;
        this.estadoKai = estadoKai;
        this.mensajeMotivacional = mensajeMotivacional;
        this.xpTotal = xpTotal;
        this.rachaActual = rachaActual;
        this.habitosDiarios = habitosDiarios;
        this.progresoDiario = progresoDiario;
    }

    public boolean isLoading() {
        return loading;
    }

    public boolean isSuccess() {
        return success;
    }

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
