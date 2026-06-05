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
import com.roma.kai.model.entity.UsuarioEntity;
import com.roma.kai.session.SessionManager;
import com.roma.kai.utils.Event;
import com.roma.kai.utils.UiMessage;

import okhttp3.MultipartBody;

public class PerfilViewModel extends AndroidViewModel {
    private final SessionManager sessionManager;
    private final PerfilRepository perfilRepository;
    private final MutableLiveData<PerfilUiState> perfilUiState = new MutableLiveData<>();
    private final MutableLiveData<Event<UiMessage>> eventUiMessage = new MutableLiveData<>();

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        sessionManager = SessionManager.getInstance(application);
        perfilRepository = new PerfilRepository(RetrofitClient.getService(application));
        loadPerfil();
    }

    public LiveData<PerfilUiState> getPerfilUiState() { return perfilUiState; }

    public LiveData<Event<UiMessage>> getEventUiMessage() { return eventUiMessage; }

    public void loadPerfil() {
        perfilUiState.setValue(new PerfilUiState(
                false,
                true,
                sessionManager.getUser()
        ));
    }

    public void uploadProfileImage(MultipartBody.Part image) {
        perfilUiState.setValue(new PerfilUiState(true, false, sessionManager.getUser()));
        
        perfilRepository.uploadImage(image, new RepositoryCallback<ImageResponse>() {
            @Override
            public void onSuccess(ImageResponse data) {
                UsuarioEntity user = sessionManager.getUser();
                if (user != null) {
                    user.setFotoPerfil(data.getFotoPerfil());
                    sessionManager.saveUser(user);
                }
                loadPerfil();
                eventUiMessage.setValue(new Event<>(new UiMessage("Imagen actualizada", UiMessage.Type.SUCCESS)));
            }

            @Override
            public void onError(String error) {
                loadPerfil();
                eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
            }
        });
    }

    public void deleteProfileImage() {
        perfilUiState.setValue(new PerfilUiState(true, false, sessionManager.getUser()));
        
        perfilRepository.deleteImage(new RepositoryCallback<Object>() {
            @Override
            public void onSuccess(Object data) {
                UsuarioEntity user = sessionManager.getUser();
                if (user != null) {
                    user.setFotoPerfil(null);
                    sessionManager.saveUser(user);
                }
                loadPerfil();
                eventUiMessage.setValue(new Event<>(new UiMessage("Imagen eliminada", UiMessage.Type.SUCCESS)));
            }

            @Override
            public void onError(String error) {
                loadPerfil();
                eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
            }
        });
    }

    public void logout() {
        sessionManager.logout();
    }
}
