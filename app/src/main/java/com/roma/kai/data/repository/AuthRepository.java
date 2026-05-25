package com.roma.kai.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.remote.ApiService;
import com.roma.kai.data.remote.error.ApiErrorParser;
import com.roma.kai.model.dto.TokenDto;
import com.roma.kai.model.dto.ValidateTokenResponse;
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
                if(response.isSuccessful() && response.body() != null) {
                    sessionManager.saveToken(response.body().getData().getToken());
                    callback.onSuccess(response.body().getData());
                } else {

                    callback.onError(ApiErrorParser.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseData<TokenDto>> call, Throwable throwable) {
                callback.onError("MSG DE ERROR GENERICO PARA EL SISTEMA");
            }
        });
    }

    public void register(RegisterRequest registerRequest, RepositoryCallback<TokenDto> callback) {
        Call<ResponseData<TokenDto>> call = apiService.register(registerRequest);
        call.enqueue(new Callback<ResponseData<TokenDto>>() {
            @Override
            public void onResponse(Call<ResponseData<TokenDto>> call, Response<ResponseData<TokenDto>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    sessionManager.saveToken(response.body().getData().getToken());
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onError(ApiErrorParser.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseData<TokenDto>> call, Throwable throwable) {
                //agregar log correcto
               callback.onError("MSG DE ERROR GENERICO PARA EL SISTEMA");
            }
        });
    }

    public void validate(RepositoryCallback<ValidateTokenResponse> callback) {
        Call<ResponseData<ValidateTokenResponse>> call = apiService.validate();
        call.enqueue(new Callback<ResponseData<ValidateTokenResponse>>() {
            @Override
            public void onResponse(Call<ResponseData<ValidateTokenResponse>> call, Response<ResponseData<ValidateTokenResponse>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    sessionManager.saveToken(response.body().getData().getToken());
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onError(ApiErrorParser.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseData<ValidateTokenResponse>> call, Throwable throwable) {
                callback.onError("MSG DE ERROR GENERICO PARA EL SISTEMA");
            }
        });
    }
}
