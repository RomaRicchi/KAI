package com.roma.kai.ui.register;

import com.roma.kai.model.dto.AuthUserResponse;

public class RegisterUiState {
    private final boolean loading;
    private final boolean success;
    private final AuthUserResponse authUser;
    
    private final String nameError;
    private final String usernameError;
    private final String emailError;
    private final String passwordError;
    private final String confirmPasswordError;

    public RegisterUiState(boolean loading, boolean success, AuthUserResponse authUser) {
        this(loading, success, authUser, null, null, null, null, null);
    }

    public RegisterUiState(boolean loading, boolean success, AuthUserResponse authUser, 
                          String nameError, String usernameError, String emailError, String passwordError, String confirmPasswordError) {
        this.loading = loading;
        this.success = success;
        this.authUser = authUser;
        this.nameError = nameError;
        this.usernameError = usernameError;
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.confirmPasswordError = confirmPasswordError;
    }

    public static RegisterUiState error(String nameError, String usernameError, String emailError, String passwordError, String confirmPasswordError) {
        return new RegisterUiState(false, false, null, nameError, usernameError, emailError, passwordError, confirmPasswordError);
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

    public String getNameError() {
        return nameError;
    }

    public String getUsernameError() {
        return usernameError;
    }

    public String getEmailError() {
        return emailError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public String getConfirmPasswordError() {
        return confirmPasswordError;
    }
}
