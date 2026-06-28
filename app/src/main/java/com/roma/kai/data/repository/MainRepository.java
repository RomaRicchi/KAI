package com.roma.kai.data.repository;

import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.remote.ApiService;
import com.roma.kai.data.remote.error.ApiErrorParser;
import com.roma.kai.model.dto.MeResponse;
import com.roma.kai.model.response.ResponseData;
import com.roma.kai.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {
    private final SessionManager sessionManager;
    private final ApiService apiService;

    public MainRepository(SessionManager sessionManager, ApiService apiService) {
        this.sessionManager = sessionManager;
        this.apiService = apiService;
    }

    public void loadMe(RepositoryCallback<MeResponse> callback) {
        apiService.getMe().enqueue(new Callback<ResponseData<MeResponse>>() {
            @Override
            public void onResponse(Call<ResponseData<MeResponse>> call, Response<ResponseData<MeResponse>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    MeResponse data = response.body().getData();
                    sessionManager.saveUser(data.getUsuario());
                    sessionManager.saveConfig(data.getConfiguracionUsuario());
                    callback.onSuccess(data);
                } else {
                    callback.onError(ApiErrorParser.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseData<MeResponse>> call, Throwable t) {
                callback.onError("Error de conexión");
            }
        });
    }
}
