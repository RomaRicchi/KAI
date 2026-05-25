package com.roma.kai.ui.perfil;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.session.SessionManager;

public class PerfilViewModel extends AndroidViewModel {
    private final SessionManager sessionManager;
    private final MutableLiveData<PerfilUiState> perfilUiState = new MutableLiveData<>();
    public PerfilViewModel(@NonNull Application application) {
        super(application);
        sessionManager = SessionManager.getInstance(application);
        loadPerfil();
    }

    public LiveData<PerfilUiState> getPerfilUiState() {
        return perfilUiState;
    }

    public void loadPerfil() {
        perfilUiState.setValue(new PerfilUiState(
                false,
                true,
                sessionManager.getUser(),
                null
        ));
    }
}
