package com.roma.kai.model.dto;

import com.google.gson.annotations.SerializedName;

public class ImageResponse {
    @SerializedName("foto_perfil")
    private String fotoPerfil;

    public String getFotoPerfil() {
        return fotoPerfil;
    }
}
