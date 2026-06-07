package com.roma.kai.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KaiDashboarResponse {
    @SerializedName("estado_kai")
    private KaiStateResponse estadoKai;
    @SerializedName("mensaje_emocional")
    private String mensajeEmocional;
    private List<KaiAttributeDto> atributos;
    @SerializedName("categoria_dominante")
    private NombreValorDto categoriaDominante;
    @SerializedName("categoria_menos_dominante")
    private NombreValorDto categoriaMenosDominante;
    @SerializedName("progreso_diario")
    private DailyProgress progresoDiario;

    public KaiStateResponse getEstadoKai() {
        return estadoKai;
    }

    public String getMensajeEmocional() {
        return mensajeEmocional;
    }

    public List<KaiAttributeDto> getAtributos() {
        return atributos;
    }

    public NombreValorDto getCategoriaDominante() {
        return categoriaDominante;
    }

    public NombreValorDto getCategoriaMenosDominante() {
        return categoriaMenosDominante;
    }

    public DailyProgress getProgresoDiario() {
        return progresoDiario;
    }
}
