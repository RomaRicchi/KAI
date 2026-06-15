package com.roma.kai.ui.login;

import com.roma.kai.model.dto.AuthUserResponse;
import com.roma.kai.utils.Event;

public class LoginUiState {
    private final boolean loading;
    private final boolean success;
    private final AuthUserResponse authUser;

    public LoginUiState(boolean loading, boolean success, AuthUserResponse authUser) {
        this.loading = loading;
        this.success = success;
        this.authUser = authUser;
    }

    public boolean isLoading() {
        return loading;
    }

    public boolean isSuccess() {
        return success;
    }

    public AuthUserResponse getAuthUser() {
        return authUser;
    }
}
