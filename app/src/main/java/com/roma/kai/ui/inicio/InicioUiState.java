package com.roma.kai.ui.inicio;

import com.roma.kai.model.dto.DailyHabitSummary;
import com.roma.kai.model.dto.KaiHomeSummary;

import java.util.List;

public class InicioUiState {
    private final boolean isLoading;
    private final boolean isSuccess;
    private final String formattedXp;
    private final String formattedRacha;
    private final String mensajeMotivacional;
    private final String kaiImageKey;
    private final List<DailyHabitSummary> habitosDiarios;

    public InicioUiState(
            boolean isLoading,
            boolean isSuccess,
            String formattedXp,
            String formattedRacha,
            String mensajeMotivacional,
            String kaiImageKey,
            List<DailyHabitSummary> habitosDiarios
    ) {
        this.isLoading = isLoading;
        this.isSuccess = isSuccess;
        this.formattedXp = formattedXp;
        this.formattedRacha = formattedRacha;
        this.mensajeMotivacional = mensajeMotivacional;
        this.kaiImageKey = kaiImageKey;
        this.habitosDiarios = habitosDiarios;
    }

    public boolean isLoading() { return isLoading; }
    public boolean isSuccess() { return isSuccess; }
    public String getFormattedXp() { return formattedXp; }
    public String getFormattedRacha() { return formattedRacha; }
    public String getMensajeMotivacional() { return mensajeMotivacional; }
    public String getKaiImageKey() { return kaiImageKey; }
    public List<DailyHabitSummary> getHabitosDiarios() { return habitosDiarios; }

    public static InicioUiState loading() {
        return new InicioUiState(true, false, "", "", "", null, null);
    }

    public static InicioUiState error() {
        return new InicioUiState(false, false, "", "", "", null, null);
    }
}
