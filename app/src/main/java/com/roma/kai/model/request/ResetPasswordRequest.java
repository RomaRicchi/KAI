package com.roma.kai.model.request;

public class ResetPasswordRequest {
    private String email;
    private String codigo;
    private String password;

    public ResetPasswordRequest(String email, String codigo, String password) {
        this.email = email;
        this.codigo = codigo;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
