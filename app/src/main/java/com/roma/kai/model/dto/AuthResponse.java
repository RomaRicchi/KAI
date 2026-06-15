package com.roma.kai.model.dto;

public class AuthResponse {
    private AuthUserResponse usuario;
    private String token;

    public AuthUserResponse getUsuario() {
        return usuario;
    }

    public String getToken() {
        return token;
    }
}
