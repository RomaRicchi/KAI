package com.roma.kai.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.remote.ApiService;
import com.roma.kai.data.remote.error.ApiErrorParser;
import com.roma.kai.model.dto.TokenDto;
import com.roma.kai.model.dto.ValidateTokenResponse;
import com.roma.kai.model.request.GoogleLoginRequest;
import com.roma.kai.model.request.LoginRequest;
import com.roma.kai.model.request.RegisterRequest;
import com.roma.kai.model.response.ResponseData;
import com.roma.kai.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private final SessionManager sessionManager;
    private final ApiService apiService;
    public AuthRepository(SessionManager sessionManager, ApiService apiService) {
        this.sessionManager = sessionManager;
        this.apiService = apiService;
    }

    public void login(LoginRequest loginRequest, RepositoryCallback<TokenDto> callback) {
        Call<ResponseData<TokenDto>> call = apiService.login(loginRequest);
        call.enqueue(new Callback<ResponseData<TokenDto>>() {
            @Override
            public void onResponse(Call<ResponseData<TokenDto>> call, Response<ResponseData<TokenDto>> response) {
                if(response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    sessionManager.saveToken(response.body().getData().getToken());
                    callback.onSuccess(response.body().getData());
                } else if (response.isSuccessful()) {
                    callback.onError("La respuesta del servidor no contiene datos");
                } else {
                    callback.onError(ApiErrorParser.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseData<TokenDto>> call, Throwable throwable) {
                Log.e("AuthRepository", "Error de login: " + throwable.getMessage(), throwable);
                callback.onError("Error de conexión: " + throwable.getMessage());
            }
        });
    }

    public void googleLogin(GoogleLoginRequest googleLoginRequest, RepositoryCallback<TokenDto> callback) {
        Call<ResponseData<TokenDto>> call = apiService.googleLogin(googleLoginRequest);
        call.enqueue(new Callback<ResponseData<TokenDto>>() {
            @Override
            public void onResponse(Call<ResponseData<TokenDto>> call, Response<ResponseData<TokenDto>> response) {
                if(response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    sessionManager.saveToken(response.body().getData().getToken());
                    callback.onSuccess(response.body().getData());
                } else if (response.isSuccessful()) {
                    callback.onError("La respuesta del servidor no contiene datos");
                } else {
                    callback.onError(ApiErrorParser.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseData<TokenDto>> call, Throwable throwable) {
                Log.e("AuthRepository", "Error de login: " + throwable.getMessage(), throwable);
                callback.onError("Error de conexión: " + throwable.getMessage());
            }
        });
    }

    public void register(RegisterRequest registerRequest, RepositoryCallback<TokenDto> callback) {
        Call<ResponseData<TokenDto>> call = apiService.register(registerRequest);
        call.enqueue(new Callback<ResponseData<TokenDto>>() {
            @Override
            public void onResponse(Call<ResponseData<TokenDto>> call, Response<ResponseData<TokenDto>> response) {
                if(response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    sessionManager.saveToken(response.body().getData().getToken());
                    callback.onSuccess(response.body().getData());
                } else if (response.isSuccessful()) {
                    callback.onError("La respuesta del servidor no contiene datos");
                } else {
                    callback.onError(ApiErrorParser.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseData<TokenDto>> call, Throwable throwable) {
                Log.e("AuthRepository", "Error de registro: " + throwable.getMessage(), throwable);
                callback.onError("Error de conexión: " + throwable.getMessage());
            }
        });
    }

    public void validate(RepositoryCallback<ValidateTokenResponse> callback) {
        Call<ResponseData<ValidateTokenResponse>> call = apiService.validate();
        call.enqueue(new Callback<ResponseData<ValidateTokenResponse>>() {
            @Override
            public void onResponse(Call<ResponseData<ValidateTokenResponse>> call, Response<ResponseData<ValidateTokenResponse>> response) {
                if(response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    sessionManager.saveToken(response.body().getData().getToken());
                    callback.onSuccess(response.body().getData());
                } else if (response.isSuccessful()) {
                    callback.onError("La respuesta del servidor no contiene datos");
                } else {
                    callback.onError(ApiErrorParser.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseData<ValidateTokenResponse>> call, Throwable throwable) {
                Log.e("AuthRepository", "Error de validación: " + throwable.getMessage(), throwable);
                callback.onError("Error de conexión: " + throwable.getMessage());
            }
        });
    }
}
