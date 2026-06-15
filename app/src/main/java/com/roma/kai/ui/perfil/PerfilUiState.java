package com.roma.kai.ui.perfil;

import com.roma.kai.model.entity.UsuarioEntity;

public class PerfilUiState {
    private final boolean loading;
    private final boolean success;
    private final UsuarioEntity usuario;

    public PerfilUiState(boolean loading, boolean success, UsuarioEntity usuario) {
        this.loading = loading;
        this.success = success;
        this.usuario = usuario;
    }

    public boolean isLoading() {
        return loading;
    }

    public boolean isSuccess() {
        return success;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public static PerfilUiState loading() {
        return new PerfilUiState(true, false, null);
    }

    public static PerfilUiState error() {
        return new PerfilUiState(false, false, null);
    }
}
