package com.roma.kai.ui.login;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.repository.AuthRepository;
import com.roma.kai.model.dto.TokenDto;
import com.roma.kai.model.request.LoginRequest;
import com.roma.kai.model.response.ResponseData;
import com.roma.kai.data.remote.ApiService;
import com.roma.kai.data.remote.RetrofitClient;
import com.roma.kai.session.SessionManager;
import com.roma.kai.utils.Event;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private final AuthRepository authRepository;
    private final MutableLiveData<LoginUiState> uiState = new MutableLiveData<>();
    public LoginViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(
                SessionManager.getInstance(application),
                RetrofitClient.getService(application)
        );
    }

    public LiveData<LoginUiState> getUiState() { return uiState; }

    public void login(String email, String password) {
        //validar

        LoginRequest loginRequest = new LoginRequest(email, password);
        uiState.setValue(new LoginUiState(true, false, null));
        authRepository.login(loginRequest, new RepositoryCallback<TokenDto>() {
            @Override
            public void onSuccess(TokenDto data) {
                uiState.setValue(new LoginUiState(
                        true,
                        true,
                        new Event<>("SOLICITUD CORRECTA")
                ));
            }

            @Override
            public void onError(String error) {
                uiState.setValue(new LoginUiState(
                        false,
                        false,
                        new Event<>(error)
                ));
            }
        });
    }
}
