package com.roma.kai.ui.inicio;

import com.roma.kai.model.dto.DailyHabitSummary;
import com.roma.kai.model.dto.KaiHomeSummary;

import java.util.List;

public class InicioUiState {
    private final boolean isLoading;
    private final boolean isSuccess;
    private final KaiHomeSummary estadoKai;
    private final String mensajeMotivacional;
    private final String formattedXp;
    private final String formattedRacha;
    private final List<DailyHabitSummary> habitosDiarios;

    public InicioUiState(boolean isLoading, boolean isSuccess, KaiHomeSummary estadoKai, String mensajeMotivacional, String formattedXp, String formattedRacha, List<DailyHabitSummary> habitosDiarios) {
        this.isLoading = isLoading;
        this.isSuccess = isSuccess;
        this.estadoKai = estadoKai;
        this.mensajeMotivacional = mensajeMotivacional;
        this.formattedXp = formattedXp;
        this.formattedRacha = formattedRacha;
        this.habitosDiarios = habitosDiarios;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public KaiHomeSummary getEstadoKai() {
        return estadoKai;
    }

    public String getMensajeMotivacional() {
        return mensajeMotivacional;
    }

    public String getFormattedXp() {
        return formattedXp;
    }

    public String getFormattedRacha() {
        return formattedRacha;
    }

    public List<DailyHabitSummary> getHabitosDiarios() {
        return habitosDiarios;
    }

    public static InicioUiState loading() {
        return new InicioUiState(true, false, null, "", "", "", null);
    }

    public static InicioUiState error() {
        return new InicioUiState(false, false, null, "", "", "", null);
    }
}
