package com.roma.kai.ui.register;

import com.roma.kai.utils.Event;

public class RegisterUiState {
    private final boolean loading;
    private final boolean success;

    public RegisterUiState(boolean loading, boolean success) {
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
