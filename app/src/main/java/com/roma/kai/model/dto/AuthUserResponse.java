package com.roma.kai.model.dto;

import java.io.Serializable;

public class AuthUserResponse implements Serializable {
    private String nombre;
    private String email;

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }
}
