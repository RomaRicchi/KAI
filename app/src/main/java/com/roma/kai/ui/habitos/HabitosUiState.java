package com.roma.kai.ui.habitos;

import com.roma.kai.model.dto.DailyProgress;
import com.roma.kai.model.dto.UserHabitResponse;

import java.util.List;

public class HabitosUiState {
    private final boolean isLoading;
    private final boolean isSuccess;
    private final String formattedProgress;
    private final List<UserHabitResponse> habitosUsuario;
    private final boolean isEmpty;

    public HabitosUiState(
            boolean isLoading,
            boolean isSuccess,
            String formattedProgress,
            List<UserHabitResponse> habitosUsuario,
            boolean isEmpty
    ) {
        this.isLoading = isLoading;
        this.isSuccess = isSuccess;
        this.formattedProgress = formattedProgress;
        this.habitosUsuario = habitosUsuario;
        this.isEmpty = isEmpty;
    }

    public boolean isLoading() { return isLoading; }
    public boolean isSuccess() { return isSuccess; }
    public String getFormattedProgress() { return formattedProgress; }
    public List<UserHabitResponse> getHabitosUsuario() { return habitosUsuario; }
    public boolean isEmpty() { return isEmpty; }

    public static HabitosUiState loading() {
        return new HabitosUiState(true, false, "", null, false);
    }

    public static HabitosUiState error() {
        return new HabitosUiState(false, false, "", null, false);
    }
}
