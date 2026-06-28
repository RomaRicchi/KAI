package com.roma.kai.ui.perfil;

import com.roma.kai.model.dto.ProfileResponse;
import com.roma.kai.model.entity.UsuarioEntity;

public class PerfilUiState {
    private final boolean loading;
    private final boolean saving;
    private final boolean success;
    private final ProfileResponse profile;

    public PerfilUiState(boolean loading, boolean saving, boolean success, ProfileResponse profile) {
        this.loading = loading;
        this.saving = saving;
        this.success = success;
        this.profile = profile;
    }

    public boolean isLoading() {
        return loading;
    }

    public boolean isSaving() {
        return saving;
    }

    public boolean isSuccess() {
        return success;
    }

    public ProfileResponse getProfile() {
        return profile;
    }

    public UsuarioEntity getUsuario() {
        return profile == null ? null : profile.getUsuario();
    }

    public static PerfilUiState loading(ProfileResponse currentProfile) {
        return new PerfilUiState(true, false, false, currentProfile);
    }

    public static PerfilUiState saving(ProfileResponse currentProfile) {
        return new PerfilUiState(false, true, true, currentProfile);
    }

    public static PerfilUiState success(ProfileResponse profile) {
        return new PerfilUiState(false, false, true, profile);
    }

    public static PerfilUiState error(ProfileResponse currentProfile) {
        return new PerfilUiState(false, false, false, currentProfile);
    }
}
