package com.roma.kai.model.request;

public class UpdateProfileRequest {
    private String nombre;
    private String email;

    public UpdateProfileRequest(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
}
