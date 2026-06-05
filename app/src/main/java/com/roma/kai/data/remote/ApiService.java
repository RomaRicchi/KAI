package com.roma.kai.data.remote;

import com.roma.kai.model.dto.CategoriaDto;
import com.roma.kai.model.dto.HabitoCatalogoDto;
import com.roma.kai.model.dto.HabitsViewResponse;
import com.roma.kai.model.dto.HomeResponse;
import com.roma.kai.model.dto.MeResponse;
import com.roma.kai.model.dto.TokenDto;
import com.roma.kai.model.dto.ValidateTokenResponse;
import com.roma.kai.model.request.LoginRequest;
import com.roma.kai.model.request.RegisterRequest;
import com.roma.kai.model.request.SelectHabitRequest;
import com.roma.kai.model.response.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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
    Call<ResponseData<HomeResponse>> getHomeView();

    //api para la vista Habitos
    @GET("api/v1/habits")
    Call<ResponseData<HabitsViewResponse>> getHabitsView();

    // Categorías y catálogo de hábitos
    @GET("api/v1/habits/categories")
    Call<ResponseData<List<CategoriaDto>>> getCategories();

    @GET("api/v1/habits/catalog")
    Call<ResponseData<List<HabitoCatalogoDto>>> getCatalog(@Query("category_id") String categoryId);

    @POST("api/v1/habits/select")
    Call<ResponseData<Object>> selectHabit(@Body SelectHabitRequest request);

    @GET("api/v1/habits/{habitUserId}")
    Call<ResponseData<com.roma.kai.model.dto.HabitDetailResponse>> getHabitDetail(
            @retrofit2.http.Path("habitUserId") String habitUserId
    );

    @retrofit2.http.DELETE("api/v1/habits/{habitUserId}/deactivate")
    Call<ResponseData<Object>> deactivateHabit(
            @retrofit2.http.Path("habitUserId") String habitUserId
    );

    @POST("api/v1/habits/{habitUserId}/complete")
    Call<ResponseData<Object>> completeHabit(
            @retrofit2.http.Path("habitUserId") String habitUserId,
            @retrofit2.http.Body com.roma.kai.model.request.CompleteHabitRequest request
    );

    // --- Perfil e Imagen ---
    @retrofit2.http.Multipart
    @POST("api/v1/users/profile/image")
    Call<ResponseData<com.roma.kai.model.dto.ImageResponse>> uploadProfileImage(
            @retrofit2.http.Part okhttp3.MultipartBody.Part image
    );

    @retrofit2.http.DELETE("api/v1/users/profile/image")
    Call<ResponseData<Object>> deleteProfileImage();
}
