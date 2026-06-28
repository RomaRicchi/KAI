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
import com.roma.kai.model.dto.ProfileResponse;
import com.roma.kai.model.entity.UsuarioEntity;
import com.roma.kai.model.request.UpdateProfileRequest;
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

    public LiveData<PerfilUiState> getPerfilUiState() {
        return perfilUiState;
    }

    public LiveData<Event<UiMessage>> getEventUiMessage() {
        return eventUiMessage;
    }

    public void loadPerfil() {
        ProfileResponse current = currentProfile();
        perfilUiState.setValue(PerfilUiState.loading(current));

        perfilRepository.loadProfile(new RepositoryCallback<ProfileResponse>() {
            @Override
            public void onSuccess(ProfileResponse data) {
                perfilUiState.setValue(PerfilUiState.success(data));
            }

            @Override
            public void onError(String error) {
                perfilUiState.setValue(PerfilUiState.error(current));
                emitError(error);
            }
        });
    }

    public void updateProfile(String name, String username) {
        ProfileResponse current = currentProfile();
        if (current == null || current.getUsuario() == null) {
            return;
        }

        String cleanName = name == null ? "" : name.trim();
        String cleanUsername = username == null ? "" : username.trim();
        if (cleanName.length() < 2) {
            emitError("El nombre debe tener al menos 2 caracteres");
            return;
        }
        if (!cleanUsername.isEmpty() && cleanUsername.length() < 3) {
            emitError("El username debe tener al menos 3 caracteres");
            return;
        }

        UsuarioEntity user = current.getUsuario();
        String changedName = cleanName.equals(user.getNombre()) ? null : cleanName;
        String currentUsername = user.getUsername() == null ? "" : user.getUsername();
        if (cleanUsername.isEmpty() && !currentUsername.isEmpty()) {
            emitError("El username no puede quedar vacío");
            return;
        }
        String changedUsername = cleanUsername.equals(currentUsername) ? null : cleanUsername;

        if (changedName == null && changedUsername == null) {
            eventUiMessage.setValue(new Event<>(new UiMessage(
                    "No hay cambios para guardar",
                    UiMessage.Type.INFO
            )));
            return;
        }

        perfilUiState.setValue(PerfilUiState.saving(current));
        perfilRepository.updateProfile(
                new UpdateProfileRequest(changedName, changedUsername, null),
                new RepositoryCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        reloadAfterUpdate("Perfil actualizado correctamente");
                    }

                    @Override
                    public void onError(String error) {
                        perfilUiState.setValue(PerfilUiState.success(current));
                        emitError(error);
                    }
                }
        );
    }

    public void uploadProfileImage(MultipartBody.Part image) {
        ProfileResponse current = currentProfile();
        if (current == null) return;
        perfilUiState.setValue(PerfilUiState.saving(current));

        perfilRepository.uploadImage(image, new RepositoryCallback<ImageResponse>() {
            @Override
            public void onSuccess(ImageResponse data) {
                if (current.getUsuario() != null) {
                    current.getUsuario().setFotoPerfil(data.getFotoPerfil());
                }
                perfilUiState.setValue(PerfilUiState.success(current));
                eventUiMessage.setValue(new Event<>(new UiMessage(
                        "Imagen actualizada",
                        UiMessage.Type.SUCCESS
                )));
            }

            @Override
            public void onError(String error) {
                perfilUiState.setValue(PerfilUiState.success(current));
                emitError(error);
            }
        });
    }

    public void deleteProfileImage() {
        ProfileResponse current = currentProfile();
        if (current == null) return;
        perfilUiState.setValue(PerfilUiState.saving(current));

        perfilRepository.deleteImage(new RepositoryCallback<Object>() {
            @Override
            public void onSuccess(Object data) {
                if (current.getUsuario() != null) {
                    current.getUsuario().setFotoPerfil(null);
                }
                perfilUiState.setValue(PerfilUiState.success(current));
                eventUiMessage.setValue(new Event<>(new UiMessage(
                        "Imagen eliminada",
                        UiMessage.Type.SUCCESS
                )));
            }

            @Override
            public void onError(String error) {
                perfilUiState.setValue(PerfilUiState.success(current));
                emitError(error);
            }
        });
    }

    private void reloadAfterUpdate(String successMessage) {
        perfilRepository.loadProfile(new RepositoryCallback<ProfileResponse>() {
            @Override
            public void onSuccess(ProfileResponse data) {
                perfilUiState.setValue(PerfilUiState.success(data));
                eventUiMessage.setValue(new Event<>(new UiMessage(
                        successMessage,
                        UiMessage.Type.SUCCESS
                )));
            }

            @Override
            public void onError(String error) {
                perfilUiState.setValue(PerfilUiState.error(currentProfile()));
                emitError(error);
            }
        });
    }

    private ProfileResponse currentProfile() {
        PerfilUiState state = perfilUiState.getValue();
        return state == null ? null : state.getProfile();
    }

    private void emitError(String error) {
        eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
    }
}
