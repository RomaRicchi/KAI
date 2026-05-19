package com.roma.kai.ui.login;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.roma.kai.model.LoginRequest;
import com.roma.kai.model.RegisterRequest;
import com.roma.kai.network.ApiService;
import com.roma.kai.network.RetrofitClient;
import com.roma.kai.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private ApiService apiService;
    private SessionManager sessionManager;
    public LoginViewModel(@NonNull Application application) {
        super(application);
        apiService = RetrofitClient.getService(application);
        sessionManager = SessionManager.getInstance(application);
    }

    public void login(String email, String password) {
        //validar

        LoginRequest loginRequest = new LoginRequest(email, password);
        Call<String> call = apiService.login(loginRequest);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && response.body() != null) {
                    sessionManager.saveToken(response.body());
                    //crear intent y enviar a HOME
                } else {
                    //mostrar msg de error
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                //mostrar msg de error
                Log.d("ERRORR", throwable.getMessage());
            }
        });
    }
}
