package com.roma.kai.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.remote.RetrofitClient;
import com.roma.kai.data.repository.MainRepository;
import com.roma.kai.model.dto.MeResponse;
import com.roma.kai.session.SessionManager;
import com.roma.kai.utils.Event;

public class MainViewModel extends AndroidViewModel {
    private final MainRepository mainRepository;
    private final MutableLiveData<MainUiState> mainUiState = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        mainRepository = new MainRepository(
                SessionManager.getInstance(application),
                RetrofitClient.getService(application)
        );
    }

    //dejo esto aca por si en algun momento se necesita para desactivar algo mientras se hace la peticion
    public LiveData<MainUiState> getMainUiState() { return mainUiState; }

    public void loadMe() {
        mainUiState.setValue(new MainUiState(true, false, null));
        mainRepository.loadMe(new RepositoryCallback<MeResponse>() {
            @Override
            public void onSuccess(MeResponse data) {
                mainUiState.setValue(new MainUiState(false, true, null));
            }

            @Override
            public void onError(String error) {
                mainUiState.setValue(new MainUiState(
                        false,
                        false,
                        new Event<>(error)
                ));
            }
        });
    }

}
