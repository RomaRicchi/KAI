package com.roma.kai.ui.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.data.remote.RetrofitClient;
import com.roma.kai.data.repository.MainRepository;
import com.roma.kai.session.SessionManager;

public class BaseViewModel extends AndroidViewModel {
    private final SessionManager sessionManager;
    private final MainRepository mainRepository;

    private MutableLiveData<Boolean> navigateToHome = new MutableLiveData<>();
    private MutableLiveData<Boolean> navigateToLogin = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
        sessionManager = SessionManager.getInstance(application);
        mainRepository = new MainRepository(sessionManager, RetrofitClient.getService(application));
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
        // quitar el else cuando la api /login devuelva lo mismo que /me
//        mainRepository.loadInitialData(new RepositoryCallback<BulkDataDto>() {
//            @Override
//            public void onSuccess(BulkDataDto data) {
//                navigateToHome.setValue(true);
//            }
//
//            @Override
//            public void onError(String error) {
//                navigateToLogin.setValue(true);
//            }
//        });
    }
}
