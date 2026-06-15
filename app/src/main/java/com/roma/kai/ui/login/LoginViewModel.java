package com.roma.kai.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.repository.AuthRepository;
import com.roma.kai.model.dto.AuthResponse;
import com.roma.kai.model.dto.AuthUserResponse;
import com.roma.kai.model.request.GoogleLoginRequest;
import com.roma.kai.model.request.LoginRequest;
import com.roma.kai.data.remote.RetrofitClient;
import com.roma.kai.session.SessionManager;
import com.roma.kai.utils.Event;
import com.roma.kai.utils.UiMessage;

public class LoginViewModel extends AndroidViewModel {
    private final AuthRepository authRepository;
    private final MutableLiveData<LoginUiState> uiState = new MutableLiveData<>();
    private final MutableLiveData<Event<UiMessage>> eventUiMessage = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(
                SessionManager.getInstance(application),
                RetrofitClient.getService(application)
        );
    }

    public LiveData<LoginUiState> getUiState() { return uiState; }

    public LiveData<Event<UiMessage>> getEventUiMessage() { return eventUiMessage; }

    public void login(String email, String password) {
        //validar

        LoginRequest loginRequest = new LoginRequest(email, password);
        uiState.setValue(new LoginUiState(true, false, null));

        authRepository.login(loginRequest, new RepositoryCallback<AuthResponse>() {
            @Override
            public void onSuccess(AuthResponse data) {
                uiState.setValue(new LoginUiState(
                        false,
                        true,
                        data.getUsuario()
                ));
            }

            @Override
            public void onError(String error) {
                uiState.setValue(new LoginUiState(
                        false,
                        false,
                        null
                ));
                eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
            }
        });


    }

    public void googleLogin(String idToken) {
        GoogleLoginRequest googleLoginRequest = new GoogleLoginRequest(idToken);
        authRepository.googleLogin(googleLoginRequest, new RepositoryCallback<AuthResponse>() {
            @Override
            public void onSuccess(AuthResponse data) {
                uiState.setValue(new LoginUiState(
                        false,
                        true,
                        data.getUsuario()
                ));
            }

            @Override
            public void onError(String error) {
                uiState.setValue(new LoginUiState(
                        false,
                        false,
                        null
                ));
                eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
            }
        });
    }
}
