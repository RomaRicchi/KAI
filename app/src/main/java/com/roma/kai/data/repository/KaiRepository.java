package com.roma.kai.data.repository;

import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.remote.ApiService;
import com.roma.kai.data.remote.error.ApiErrorParser;
import com.roma.kai.model.dto.HomeResponse;
import com.roma.kai.model.dto.KaiDashboarResponse;
import com.roma.kai.model.response.ResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KaiRepository {
    private final ApiService apiService;

    public KaiRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void loadTuKaiView(RepositoryCallback<KaiDashboarResponse> callback) {
        Call<ResponseData<KaiDashboarResponse>> call = apiService.getTuKaiView();
        call.enqueue(new Callback<ResponseData<KaiDashboarResponse>>() {
            @Override
            public void onResponse(Call<ResponseData<KaiDashboarResponse>> call, Response<ResponseData<KaiDashboarResponse>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onError(ApiErrorParser.parseError(response));

                }
            }

            @Override
            public void onFailure(Call<ResponseData<KaiDashboarResponse>> call, Throwable throwable) {
                callback.onError("Error de conexión");
            }
        });
    }
}
