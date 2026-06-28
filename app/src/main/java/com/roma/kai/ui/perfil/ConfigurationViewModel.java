package com.roma.kai.ui.perfil;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.remote.RetrofitClient;
import com.roma.kai.data.repository.PerfilRepository;
import com.roma.kai.model.dto.ProfileResponse;
import com.roma.kai.model.entity.ConfiguracionUsuarioEntity;
import com.roma.kai.model.request.UpdateConfigurationRequest;
import com.roma.kai.session.SessionManager;
import com.roma.kai.utils.Event;
import com.roma.kai.utils.UiMessage;

public class ConfigurationViewModel extends AndroidViewModel {
    private final PerfilRepository repository;
    private final MutableLiveData<ConfigurationUiState> uiState = new MutableLiveData<>();
    private final MutableLiveData<Event<UiMessage>> eventUiMessage = new MutableLiveData<>();

    public ConfigurationViewModel(@NonNull Application application) {
        super(application);
        repository = new PerfilRepository(
                SessionManager.getInstance(application),
                RetrofitClient.getService(application)
        );
    }

    public LiveData<ConfigurationUiState> getUiState() {
        return uiState;
    }

    public LiveData<Event<UiMessage>> getEventUiMessage() {
        return eventUiMessage;
    }

    public void loadConfiguration() {
        ConfiguracionUsuarioEntity current = currentConfiguration();
        uiState.setValue(new ConfigurationUiState(true, false, current));
        repository.loadProfile(new RepositoryCallback<ProfileResponse>() {
            @Override
            public void onSuccess(ProfileResponse data) {
                uiState.setValue(new ConfigurationUiState(
                        false,
                        false,
                        data.getConfiguracionUsuario()
                ));
            }

            @Override
            public void onError(String error) {
                uiState.setValue(new ConfigurationUiState(false, false, current));
                emitError(error);
            }
        });
    }

    public void saveConfiguration(UpdateConfigurationRequest request) {
        ConfiguracionUsuarioEntity current = currentConfiguration();
        uiState.setValue(new ConfigurationUiState(false, true, current));
        repository.updateConfiguration(request, new RepositoryCallback<String>() {
            @Override
            public void onSuccess(String data) {
                reloadAfterSave();
            }

            @Override
            public void onError(String error) {
                uiState.setValue(new ConfigurationUiState(false, false, current));
                emitError(error);
            }
        });
    }

    private void reloadAfterSave() {
        repository.loadProfile(new RepositoryCallback<ProfileResponse>() {
            @Override
            public void onSuccess(ProfileResponse data) {
                uiState.setValue(new ConfigurationUiState(
                        false,
                        false,
                        data.getConfiguracionUsuario()
                ));
                eventUiMessage.setValue(new Event<>(new UiMessage(
                        "Configuración actualizada",
                        UiMessage.Type.SUCCESS
                )));
            }

            @Override
            public void onError(String error) {
                uiState.setValue(new ConfigurationUiState(false, false, currentConfiguration()));
                emitError(error);
            }
        });
    }

    private ConfiguracionUsuarioEntity currentConfiguration() {
        ConfigurationUiState state = uiState.getValue();
        return state == null ? null : state.getConfiguration();
    }

    private void emitError(String error) {
        eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
    }
}
