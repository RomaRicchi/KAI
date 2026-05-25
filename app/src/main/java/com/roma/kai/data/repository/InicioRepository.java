package com.roma.kai.data.repository;

import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.remote.ApiService;
import com.roma.kai.data.remote.error.ApiErrorParser;
import com.roma.kai.model.dto.HomeResponse;
import com.roma.kai.model.response.ResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioRepository {
    private final ApiService apiService;

    public InicioRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void loadHome(RepositoryCallback<HomeResponse> callback) {
        Call<ResponseData<HomeResponse>> call = apiService.getHome();
        call.enqueue(new Callback<ResponseData<HomeResponse>>() {
            @Override
            public void onResponse(Call<ResponseData<HomeResponse>> call, Response<ResponseData<HomeResponse>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onError(ApiErrorParser.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseData<HomeResponse>> call, Throwable throwable) {
                callback.onError("MSG DE ERROR GENERICO PARA EL SISTEMA");
            }
        });
    }
}
