package com.roma.kai.ui.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.remote.RetrofitClient;
import com.roma.kai.data.repository.AuthRepository;
import com.roma.kai.data.repository.MainRepository;
import com.roma.kai.model.dto.ValidateTokenResponse;
import com.roma.kai.session.SessionManager;
import com.roma.kai.utils.Event;

public class BaseViewModel extends AndroidViewModel {
    private final SessionManager sessionManager;
    private final AuthRepository authRepository;
    private final MutableLiveData<Event<Boolean>> navigateToHome = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> navigateToLogin = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
        sessionManager = SessionManager.getInstance(application);
        authRepository = new AuthRepository(sessionManager, RetrofitClient.getService(application));
    }

    public LiveData<Event<Boolean>> getNavigateToHome() {
        return navigateToHome;
    }

    public LiveData<Event<Boolean>> getNavigateToLogin() {
        return navigateToLogin;
    }

    public void verificarSession() {
        String token = sessionManager.getToken();
        if (token == null || token.isEmpty()) {
            navigateToLogin.setValue(new Event<>(true));
            return;
        }

        authRepository.validate(new RepositoryCallback<ValidateTokenResponse>() {
            @Override
            public void onSuccess(ValidateTokenResponse data) {
                navigateToHome.setValue(new Event<>(true));
            }

            @Override
            public void onError(String error) {
                navigateToLogin.setValue(new Event<>(true));
            }
        });
    }
}
