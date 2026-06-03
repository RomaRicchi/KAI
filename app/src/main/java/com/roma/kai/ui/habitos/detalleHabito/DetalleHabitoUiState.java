package com.roma.kai.ui.habitos.detalleHabito;

import com.roma.kai.model.dto.HabitDetailResponse;

public class DetalleHabitoUiState {
    private final boolean loading;
    private final HabitDetailResponse habit;
    private final String error;
    private final boolean isDeactivated;

    public DetalleHabitoUiState(boolean loading, HabitDetailResponse habit, String error, boolean isDeactivated) {
        this.loading = loading;
        this.habit = habit;
        this.error = error;
        this.isDeactivated = isDeactivated;
    }

    public boolean isLoading() { return loading; }
    public HabitDetailResponse getHabit() { return habit; }
    public String getError() { return error; }
    public boolean isDeactivated() { return isDeactivated; }
}
