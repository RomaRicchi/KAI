package com.roma.kai.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.data.remote.ApiService;
import com.roma.kai.model.dto.BulkDataDto;
import com.roma.kai.model.response.ResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {
    private ApiService apiService;

    public MainRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public LiveData<BulkDataDto> loadInitialData() {
        MutableLiveData<BulkDataDto> data = new MutableLiveData<>();

        Call<ResponseData<BulkDataDto>> call = apiService.fetchBulkData();
        call.enqueue(new Callback<ResponseData<BulkDataDto>>() {
            @Override
            public void onResponse(Call<ResponseData<BulkDataDto>> call, Response<ResponseData<BulkDataDto>> response) {

            }

            @Override
            public void onFailure(Call<ResponseData<BulkDataDto>> call, Throwable throwable) {

            }
        });

        return data;
    }
}
