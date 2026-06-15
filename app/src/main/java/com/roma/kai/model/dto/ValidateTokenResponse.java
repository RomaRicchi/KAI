package com.roma.kai.model.dto;

public class ValidateTokenResponse {
    private boolean valid;
    private String token;
    private AuthUserResponse usuario;

    public boolean isValid() {
        return valid;
    }

    public String getToken() {
        return token;
    }

    public AuthUserResponse getUsuario() {
        return usuario;
    }
}
