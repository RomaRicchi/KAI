package com.roma.kai.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.data.remote.ApiService;
import com.roma.kai.data.remote.RetrofitClient;
import com.roma.kai.session.SessionManager;

public class BaseViewModel extends AndroidViewModel {
    private SessionManager sessionManager;
    private ApiService apiService;

    private MutableLiveData<Boolean> navigateToHome = new MutableLiveData<>();
    private MutableLiveData<Boolean> navigateToLogin = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
        apiService = RetrofitClient.getService(application);
        sessionManager = SessionManager.getInstance(application);
    }

    public LiveData<Boolean> getNavigateToHome() {
        return navigateToHome;
    }

    public LiveData<Boolean> getNavigateToLogin() {
        return navigateToLogin;
    }

    public void verificarSession() {
        String token = sessionManager.getToken();
        if (token == null || token.isEmpty()) {
            navigateToLogin.setValue(true);
//            return;
        } else {
            //quitar esto hasta cuando tengas una api para verificar la session
            navigateToHome.setValue(true);
        }

//        Call<Void> call = apiService.verificarSession();
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                    navigateToHome.setValue(true);
//                } else {
//                    sessionManager.clearSession();
//                    navigateToLogin.setValue(true);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable throwable) {
//                sessionManager.clearSession();
//                navigateToLogin.setValue(true);
//            }
//        });
    }
}
