package com.roma.kai.data.remote;

import com.roma.kai.model.dto.HomeResponse;
import com.roma.kai.model.dto.MeResponse;
import com.roma.kai.model.dto.TokenDto;
import com.roma.kai.model.dto.ValidateTokenResponse;
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

    @GET("api/v1/auth/validate")
    Call<ResponseData<ValidateTokenResponse>> validate();

    // Usuario y config del sistema
    @GET("api/v1/users/me")
    Call<ResponseData<MeResponse>> getMe();

    //api para la vista home "InicioFragment"
    @GET("api/v1/home")
    Call<ResponseData<HomeResponse>> getHome();
}
