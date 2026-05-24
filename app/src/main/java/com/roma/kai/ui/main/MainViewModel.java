package com.roma.kai.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.roma.kai.session.SessionManager;

public class MainViewModel extends AndroidViewModel {
    private final SessionManager sessionManager;
    public MainViewModel(@NonNull Application application) {
        super(application);
        sessionManager = SessionManager.getInstance(application);
    }

    public void logout() {
        sessionManager.logout();
    }
}
