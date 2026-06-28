package com.roma.kai.ui.perfil;

import com.roma.kai.model.entity.ConfiguracionUsuarioEntity;

public class ConfigurationUiState {
    private final boolean loading;
    private final boolean saving;
    private final ConfiguracionUsuarioEntity configuration;

    public ConfigurationUiState(boolean loading, boolean saving, ConfiguracionUsuarioEntity configuration) {
        this.loading = loading;
        this.saving = saving;
        this.configuration = configuration;
    }

    public boolean isLoading() {
        return loading;
    }

    public boolean isSaving() {
        return saving;
    }

    public ConfiguracionUsuarioEntity getConfiguration() {
        return configuration;
    }
}
