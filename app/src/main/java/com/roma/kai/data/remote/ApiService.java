package com.roma.kai.data.remote;

import com.roma.kai.model.dto.BulkDataDto;
import com.roma.kai.model.dto.TokenDto;
import com.roma.kai.model.request.LoginRequest;
import com.roma.kai.model.request.RegisterRequest;
import com.roma.kai.model.response.ResponseData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    // --- Autenticación ---
    @POST("api/v1/auth/login")
    Call<ResponseData<TokenDto>> login(@Body LoginRequest loginRequest);

    @POST("api/v1/auth/register")
    Call<ResponseData<TokenDto>> register(@Body RegisterRequest registerRequest);

    // PETICION PARA CARGA MASIVA
    @GET("api/v1/users/me")
    Call<ResponseData<BulkDataDto>> fetchBulkData();
}
