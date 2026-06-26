package com.roma.kai.data.remote;

import com.roma.kai.model.dto.AuthResponse;
import com.roma.kai.model.dto.CategoriaDto;
import com.roma.kai.model.dto.CompleteHabitResponse;
import com.roma.kai.model.dto.HabitDetailResponse;
import com.roma.kai.model.dto.HabitoCatalogoDto;
import com.roma.kai.model.dto.HabitsViewResponse;
import com.roma.kai.model.dto.HomeResponse;
import com.roma.kai.model.dto.ImageResponse;
import com.roma.kai.model.dto.KaiDashboarResponse;
import com.roma.kai.model.dto.MeResponse;
import com.roma.kai.model.dto.AuthUserResponse;
import com.roma.kai.model.dto.ValidateTokenResponse;
import com.roma.kai.model.request.CompleteHabitRequest;
import com.roma.kai.model.request.ForgotPasswordRequest;
import com.roma.kai.model.request.GoogleLoginRequest;
import com.roma.kai.model.request.LoginRequest;
import com.roma.kai.model.request.RegisterRequest;
import com.roma.kai.model.request.ResetPasswordRequest;
import com.roma.kai.model.request.SelectHabitRequest;
import com.roma.kai.model.response.ResponseData;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // --- Autenticación ---
    @POST("api/v1/auth/login")
    Call<ResponseData<AuthResponse>> login(@Body LoginRequest loginRequest);

    @POST("api/v1/auth/register")
    Call<ResponseData<AuthResponse>> register(@Body RegisterRequest registerRequest);

    @POST("api/v1/auth/google")
    Call<ResponseData<AuthResponse>> googleLogin(@Body GoogleLoginRequest googleLoginRequest);

    @POST("auth/forgot-password")
    Call<ResponseData<Object>> forgotPassword(@Body ForgotPasswordRequest request);

    @POST("auth/reset-password")
    Call<ResponseData<Object>> resetPassword(@Body ResetPasswordRequest request);


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
    Call<ResponseData<HabitDetailResponse>> getHabitDetail(@Path("habitUserId") String habitUserId);

    @DELETE("api/v1/habits/{habitUserId}/deactivate")
    Call<ResponseData<Object>> deactivateHabit(@Path("habitUserId") String habitUserId);

    @POST("api/v1/habits/{habitUserId}/complete")
    Call<ResponseData<CompleteHabitResponse>> completeHabit(@Path("habitUserId") String habitUserId, @Body CompleteHabitRequest request);

    // --- Perfil e Imagen ---
    @Multipart
    @PUT("api/v1/users/me/profile-photo")
    Call<ResponseData<ImageResponse>> uploadProfileImage(@Part MultipartBody.Part foto);

    @DELETE("api/v1/users/profile/image")
    Call<ResponseData<Object>> deleteProfileImage();

    //api para la vista Kai
    @GET("api/v1/kai")
    Call<ResponseData<KaiDashboarResponse>> getTuKaiView();
}
