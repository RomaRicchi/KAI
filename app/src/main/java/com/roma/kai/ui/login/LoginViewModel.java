package com.roma.kai.ui.login;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.model.dto.TokenDto;
import com.roma.kai.model.request.LoginRequest;
import com.roma.kai.model.response.ResponseData;
import com.roma.kai.data.remote.ApiService;
import com.roma.kai.data.remote.RetrofitClient;
import com.roma.kai.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private final ApiService apiService;
    private final SessionManager sessionManager;
    private final MutableLiveData<Boolean> navigateToHome = new MutableLiveData<>();
    public LoginViewModel(@NonNull Application application) {
        super(application);
        apiService = RetrofitClient.getService(application);
        sessionManager = SessionManager.getInstance(application);
    }

    public LiveData<Boolean> getNavigateToHome() {
        return navigateToHome;
    }

    public void login(String email, String password) {
        //validar

        LoginRequest loginRequest = new LoginRequest(email, password);
        Call<ResponseData<TokenDto>> call = apiService.login(loginRequest);
        call.enqueue(new Callback<ResponseData<TokenDto>>() {
            @Override
            public void onResponse(Call<ResponseData<TokenDto>> call, Response<ResponseData<TokenDto>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    sessionManager.saveToken(response.body().getData().getToken());
                    navigateToHome.postValue(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseData<TokenDto>> call, Throwable throwable) {
                Log.d("ERRORR", throwable.getMessage());
            }
        });
    }
}
