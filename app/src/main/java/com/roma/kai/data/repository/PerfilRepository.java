package com.roma.kai.data.repository;

import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.remote.ApiService;
import com.roma.kai.data.remote.error.ApiErrorParser;
import com.roma.kai.model.dto.ImageResponse;
import com.roma.kai.model.dto.MeResponse;
import com.roma.kai.model.dto.ProfileResponse;
import com.roma.kai.model.entity.UsuarioEntity;
import com.roma.kai.model.request.UpdateConfigurationRequest;
import com.roma.kai.model.request.UpdateProfileRequest;
import com.roma.kai.model.response.ResponseData;
import com.roma.kai.session.SessionManager;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilRepository {
    private final SessionManager sessionManager;
    private final ApiService apiService;

    public PerfilRepository(SessionManager sessionManager, ApiService apiService) {
        this.sessionManager = sessionManager;
        this.apiService = apiService;
    }


    public void loadMe(RepositoryCallback<MeResponse> callback) {
        Call<ResponseData<MeResponse>> call = apiService.getMe();
        call.enqueue(new Callback<ResponseData<MeResponse>>() {
            @Override
            public void onResponse(Call<ResponseData<MeResponse>> call, Response<ResponseData<MeResponse>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getData());
                    sessionManager.saveUser(response.body().getData().getUsuario());
                    sessionManager.saveConfig(response.body().getData().getConfiguracionUsuario());
                } else {
                    callback.onError(ApiErrorParser.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseData<MeResponse>> call, Throwable throwable) {
                callback.onError("Error de conexión");
            }
        });

    }

    public void loadProfile(RepositoryCallback<ProfileResponse> callback) {
        apiService.getProfile().enqueue(new Callback<ResponseData<ProfileResponse>>() {
            @Override
            public void onResponse(Call<ResponseData<ProfileResponse>> call, Response<ResponseData<ProfileResponse>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    ProfileResponse profile = response.body().getData();
                    UsuarioEntity cachedUser = sessionManager.getUser();
                    if (profile.getUsuario() != null && cachedUser != null &&
                            profile.getUsuario().getFotoPerfil() == null) {
                        profile.getUsuario().setFotoPerfil(cachedUser.getFotoPerfil());
                    }
                    sessionManager.saveUser(profile.getUsuario());
                    sessionManager.saveConfig(profile.getConfiguracionUsuario());
                    callback.onSuccess(profile);
                } else {
                    callback.onError(ApiErrorParser.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseData<ProfileResponse>> call, Throwable throwable) {
                callback.onError("Error de conexión");
            }
        });
    }

    public void updateProfile(UpdateProfileRequest request, RepositoryCallback<String> callback) {
        apiService.updateProfile(request).enqueue(stringCallback(callback, "Error al actualizar el perfil"));
    }

    public void updateConfiguration(UpdateConfigurationRequest request, RepositoryCallback<String> callback) {
        apiService.updateProfileConfiguration(request).enqueue(
                stringCallback(callback, "Error al actualizar la configuración")
        );
    }

    private Callback<ResponseData<String>> stringCallback(RepositoryCallback<String> callback, String connectionError) {
        return new Callback<ResponseData<String>>() {
            @Override
            public void onResponse(Call<ResponseData<String>> call, Response<ResponseData<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onError(ApiErrorParser.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseData<String>> call, Throwable throwable) {
                callback.onError(connectionError);
            }
        };
    }

    public void uploadImage(MultipartBody.Part image, RepositoryCallback<ImageResponse> callback) {
        apiService.uploadProfileImage(image).enqueue(new Callback<ResponseData<ImageResponse>>() {
            @Override
            public void onResponse(Call<ResponseData<ImageResponse>> call, Response<ResponseData<ImageResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ImageResponse imageResponse = response.body().getData();
                    UsuarioEntity cachedUser = sessionManager.getUser();
                    if (cachedUser != null && imageResponse != null) {
                        cachedUser.setFotoPerfil(imageResponse.getFotoPerfil());
                        sessionManager.saveUser(cachedUser);
                    }
                    callback.onSuccess(imageResponse);
                } else {
                    callback.onError(ApiErrorParser.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseData<ImageResponse>> call, Throwable t) {
                callback.onError("Error al subir la imagen");
            }
        });
    }

    public void deleteImage(RepositoryCallback<Object> callback) {
        apiService.deleteProfileImage().enqueue(new Callback<ResponseData<Object>>() {
            @Override
            public void onResponse(Call<ResponseData<Object>> call, Response<ResponseData<Object>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onError(ApiErrorParser.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseData<Object>> call, Throwable t) {
                callback.onError("Error al eliminar la imagen");
            }
        });
    }
}
