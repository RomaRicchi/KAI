package com.roma.kai.ui.perfil;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.remote.RetrofitClient;
import com.roma.kai.data.repository.PerfilRepository;
import com.roma.kai.model.dto.ImageResponse;
import com.roma.kai.model.dto.MeResponse;
import com.roma.kai.model.entity.UsuarioEntity;
import com.roma.kai.session.SessionManager;
import com.roma.kai.utils.Event;
import com.roma.kai.utils.UiMessage;

import okhttp3.MultipartBody;

public class PerfilViewModel extends AndroidViewModel {
    private final PerfilRepository perfilRepository;
    private final MutableLiveData<PerfilUiState> perfilUiState = new MutableLiveData<>();
    private final MutableLiveData<Event<UiMessage>> eventUiMessage = new MutableLiveData<>();

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        perfilRepository = new PerfilRepository(
                SessionManager.getInstance(application),
                RetrofitClient.getService(application)
        );
    }

    public LiveData<PerfilUiState> getPerfilUiState() { return perfilUiState; }

    public LiveData<Event<UiMessage>> getEventUiMessage() { return eventUiMessage; }

    public void loadPerfil() {
        perfilUiState.setValue(PerfilUiState.loading());

        perfilRepository.loadMe(new RepositoryCallback<MeResponse>() {
            @Override
            public void onSuccess(MeResponse data) {
                perfilUiState.setValue(new PerfilUiState(
                        false,
                        true,
                        data.getUsuario()
                ));
            }

            @Override
            public void onError(String error) {
                perfilUiState.setValue(PerfilUiState.error());
                eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
            }
        });
    }

    public void uploadProfileImage(MultipartBody.Part image) {
        PerfilUiState uiStateValue = perfilUiState.getValue();
        if(uiStateValue == null) return;

        perfilUiState.setValue(new PerfilUiState(true, false, uiStateValue.getUsuario()));
        
        perfilRepository.uploadImage(image, new RepositoryCallback<ImageResponse>() {
            @Override
            public void onSuccess(ImageResponse data) {
                UsuarioEntity user = uiStateValue.getUsuario();
                if (user != null) {
                    user.setFotoPerfil(data.getFotoPerfil());
                }
                perfilUiState.setValue(new PerfilUiState(
                        false,
                        true,
                        user
                ));
                eventUiMessage.setValue(new Event<>(new UiMessage("Imagen actualizada", UiMessage.Type.SUCCESS)));
            }

            @Override
            public void onError(String error) {
                perfilUiState.setValue(new PerfilUiState(
                        false,
                        false,
                        uiStateValue.getUsuario()
                ));
                eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
            }
        });
    }

    public void deleteProfileImage() {
        PerfilUiState uiStateValue = perfilUiState.getValue();
        if(uiStateValue == null) return;

        perfilUiState.setValue(new PerfilUiState(true, false, uiStateValue.getUsuario()));

        perfilRepository.deleteImage(new RepositoryCallback<Object>() {
            @Override
            public void onSuccess(Object data) {
                UsuarioEntity user = uiStateValue.getUsuario();
                if (user != null) {
                    user.setFotoPerfil(null);
                }
                perfilUiState.setValue(new PerfilUiState(
                        false,
                        true,
                        user
                ));
                eventUiMessage.setValue(new Event<>(new UiMessage("Imagen eliminada", UiMessage.Type.SUCCESS)));
            }

            @Override
            public void onError(String error) {
                perfilUiState.setValue(new PerfilUiState(
                        false,
                        false,
                        uiStateValue.getUsuario()
                ));
                eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
            }
        });
    }
}
