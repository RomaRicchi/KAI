package com.roma.kai.ui.login;

import com.roma.kai.utils.Event;

public class LoginUiState {
    private final boolean loading;
    private final boolean success;

    public LoginUiState(boolean loading, boolean success) {
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
