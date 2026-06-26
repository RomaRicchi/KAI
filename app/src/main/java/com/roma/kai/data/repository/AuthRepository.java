package com.roma.kai.data.repository;

import android.util.Log;

import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.remote.ApiService;
import com.roma.kai.data.remote.error.ApiErrorParser;
import com.roma.kai.model.dto.AuthResponse;
import com.roma.kai.model.dto.AuthUserResponse;
import com.roma.kai.model.dto.ValidateTokenResponse;
import com.roma.kai.model.request.ForgotPasswordRequest;
import com.roma.kai.model.request.GoogleLoginRequest;
import com.roma.kai.model.request.LoginRequest;
import com.roma.kai.model.request.RegisterRequest;
import com.roma.kai.model.request.ResetPasswordRequest;
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

    public void login(LoginRequest loginRequest, RepositoryCallback<AuthResponse> callback) {
        Call<ResponseData<AuthResponse>> call = apiService.login(loginRequest);
        call.enqueue(new Callback<ResponseData<AuthResponse>>() {
            @Override
            public void onResponse(Call<ResponseData<AuthResponse>> call, Response<ResponseData<AuthResponse>> response) {
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
            public void onFailure(Call<ResponseData<AuthResponse>> call, Throwable throwable) {
                Log.e("AuthRepository", "Error de login: " + throwable.getMessage(), throwable);
                callback.onError("Error de conexión: " + throwable.getMessage());
            }
        });
    }

    public void register(RegisterRequest registerRequest, RepositoryCallback<AuthResponse> callback) {
        Call<ResponseData<AuthResponse>> call = apiService.register(registerRequest);
        call.enqueue(new Callback<ResponseData<AuthResponse>>() {
            @Override
            public void onResponse(Call<ResponseData<AuthResponse>> call, Response<ResponseData<AuthResponse>> response) {
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
            public void onFailure(Call<ResponseData<AuthResponse>> call, Throwable throwable) {
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
                    sessionManager.clearSession();
                    callback.onError("La respuesta del servidor no contiene datos");
                } else {
                    sessionManager.clearSession();
                    callback.onError(ApiErrorParser.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseData<ValidateTokenResponse>> call, Throwable throwable) {
                sessionManager.clearSession();
                Log.e("AuthRepository", "Error de validación: " + throwable.getMessage(), throwable);
                callback.onError("Error de conexión: " + throwable.getMessage());

            }
        });
    }

    public void googleLogin(GoogleLoginRequest googleLoginRequest, RepositoryCallback<AuthResponse> callback) {
        Call<ResponseData<AuthResponse>> call = apiService.googleLogin(googleLoginRequest);
        call.enqueue(new Callback<ResponseData<AuthResponse>>() {
            @Override
            public void onResponse(Call<ResponseData<AuthResponse>> call, Response<ResponseData<AuthResponse>> response) {
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
            public void onFailure(Call<ResponseData<AuthResponse>> call, Throwable throwable) {
                Log.e("AuthRepository", "Error de login: " + throwable.getMessage(), throwable);
                callback.onError("Error de conexión: " + throwable.getMessage());
            }
        });
    }

    public void forgotPassword(ForgotPasswordRequest request, RepositoryCallback<Void> callback) {
        Call<ResponseData<Object>> call = apiService.forgotPassword(request);
        call.enqueue(new Callback<ResponseData<Object>>() {
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
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    public void resetPassword(ResetPasswordRequest request, RepositoryCallback<Void> callback) {
        Call<ResponseData<Object>> call = apiService.resetPassword(request);
        call.enqueue(new Callback<ResponseData<Object>>() {
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
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }
}

