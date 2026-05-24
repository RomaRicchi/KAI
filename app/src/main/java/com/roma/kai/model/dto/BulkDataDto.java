package com.roma.kai.model.dto;

import com.google.gson.annotations.SerializedName;
import com.roma.kai.model.entity.ConfiguracionUsuarioEntity;
import com.roma.kai.model.entity.UsuarioEntity;

import java.util.List;

public class BulkDataDto {
    private UsuarioEntity usuario;
    @SerializedName("estado_kai")
    private EstadoKaiDto estadoKaiDto;
    @SerializedName("configuracion_usuario")
    private ConfiguracionUsuarioEntity configuracionUsuario;
    @SerializedName("xp_usuario")
    private List<UsuarioCategoriaXpDto> xpUsuario;
    @SerializedName("atributos_kai")
    private List<AtributoKaiDto> atributosKai;
    private List<HabitoDto> habitos;

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public EstadoKaiDto getEstadoKaiDto() {
        return estadoKaiDto;
    }

    public ConfiguracionUsuarioEntity getConfiguracionUsuario() {
        return configuracionUsuario;
    }

    public List<UsuarioCategoriaXpDto> getXpUsuario() {
        return xpUsuario;
    }

    public List<AtributoKaiDto> getAtributosKai() {
        return atributosKai;
    }

    public List<HabitoDto> getHabitos() {
        return habitos;
    }
}
