package com.roma.kai.ui.main;

import com.roma.kai.utils.Event;

public class MainUiState {
    private final boolean loading;
    private final boolean success;

    public MainUiState(boolean loading, boolean success) {
        this.loading = loading;
        this.success = success;
    }

    public boolean isLoading() {
        return loading;
    }

    public boolean isSuccess() {
        return success;
    }
}
