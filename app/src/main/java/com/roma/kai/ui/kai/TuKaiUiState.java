package com.roma.kai.ui.kai;

import com.roma.kai.model.dto.DailyProgress;
import com.roma.kai.model.dto.KaiAttributeDto;
import com.roma.kai.model.dto.KaiStateResponse;
import com.roma.kai.model.dto.NombreValorDto;

import java.util.Collections;
import java.util.List;

public class TuKaiUiState {
    private final boolean isLoading;
    private final boolean isSuccess;
    private final KaiStateResponse estadoKai;
    private final String mensajeEmocional;
    private final List<KaiAttributeDto> atributos;
    private final NombreValorDto categoriaDominante;
    private final NombreValorDto categoriaMenosDominante;
    private final DailyProgress progresoDiario;

    public TuKaiUiState(boolean isLoading, boolean isSuccess, KaiStateResponse estadoKai, String mensajeEmocional, List<KaiAttributeDto> atributos, NombreValorDto categoriaDominante, NombreValorDto categoriaMenosDominante, DailyProgress progresoDiario) {
        this.isLoading = isLoading;
        this.isSuccess = isSuccess;
        this.estadoKai = estadoKai;
        this.mensajeEmocional = mensajeEmocional;
        this.atributos = atributos;
        this.categoriaDominante = categoriaDominante;
        this.categoriaMenosDominante = categoriaMenosDominante;
        this.progresoDiario = progresoDiario;
    }

    public static TuKaiUiState loading() {
        return new TuKaiUiState(true, false, null, "", Collections.emptyList(), null, null, null);
    }

    public static TuKaiUiState error() {
        return new TuKaiUiState(false, false, null, "", Collections.emptyList(), null, null, null);
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

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
