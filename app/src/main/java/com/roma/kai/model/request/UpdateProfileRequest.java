package com.roma.kai.model.request;

public class UpdateProfileRequest {
    private String nombre;
    private String username;
    @com.google.gson.annotations.SerializedName("perfil_base")
    private String perfilBase;

    public UpdateProfileRequest(String nombre, String username, String perfilBase) {
        this.nombre = nombre;
        this.username = username;
        this.perfilBase = perfilBase;
    }

    public String getNombre() { return nombre; }
    public String getUsername() { return username; }
    public String getPerfilBase() { return perfilBase; }
}
