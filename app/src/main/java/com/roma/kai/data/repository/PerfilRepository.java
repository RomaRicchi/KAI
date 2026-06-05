package com.roma.kai.data.repository;

import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.remote.ApiService;
import com.roma.kai.data.remote.error.ApiErrorParser;
import com.roma.kai.model.dto.ImageResponse;
import com.roma.kai.model.response.ResponseData;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilRepository {
    private final ApiService apiService;

    public PerfilRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void uploadImage(MultipartBody.Part image, RepositoryCallback<ImageResponse> callback) {
        apiService.uploadProfileImage(image).enqueue(new Callback<ResponseData<ImageResponse>>() {
            @Override
            public void onResponse(Call<ResponseData<ImageResponse>> call, Response<ResponseData<ImageResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getData());
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
