package com.roma.kai.model.dto;

import com.google.gson.annotations.SerializedName;
import com.roma.kai.model.entity.ConfiguracionUsuarioEntity;
import com.roma.kai.model.entity.UsuarioEntity;

public class ProfileResponse {
    private UsuarioEntity usuario;
    @SerializedName("configuracion_usuario")
    private ConfiguracionUsuarioEntity configuracionUsuario;
    private ProfileSummary resumen;

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public ConfiguracionUsuarioEntity getConfiguracionUsuario() {
        return configuracionUsuario;
    }

    public ProfileSummary getResumen() {
        return resumen;
    }
}
