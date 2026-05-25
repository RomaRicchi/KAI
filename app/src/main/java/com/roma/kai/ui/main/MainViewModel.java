package com.roma.kai.ui.main;

import android.app.Application;
import android.content.Intent;

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
import com.roma.kai.utils.UiMessage;

public class MainViewModel extends AndroidViewModel {
    private final MainRepository mainRepository;
    private final MutableLiveData<MainUiState> mainUiState = new MutableLiveData<>();
    private final MutableLiveData<Event<UiMessage>> eventUiMessage = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        mainRepository = new MainRepository(
                SessionManager.getInstance(application),
                RetrofitClient.getService(application)
        );
    }

    public LiveData<MainUiState> getMainUiState() { return mainUiState; }

    public LiveData<Event<UiMessage>> getEventUiMessage() { return eventUiMessage; }

    public void loadMe() {
        mainUiState.setValue(new MainUiState(true, false));

        mainRepository.loadMe(new RepositoryCallback<MeResponse>() {
            @Override
            public void onSuccess(MeResponse data) {
//                mainUiState.setValue(new MainUiState(false, true));
//                eventUiMessage.setValue(new Event<>(new UiMessage("CARGA COMPLETA", UiMessage.Type.SUCCESS)));
            }

            @Override
            public void onError(String error) {
//                mainUiState.setValue(new MainUiState(false, false));
                eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
            }
        });
    }

    public void loadIntent(Intent intent) {
        UiMessage uiMessage = (UiMessage) intent.getSerializableExtra("messageTo");

        if(uiMessage != null) {
            eventUiMessage.setValue(new Event<>(uiMessage));
        }
    }
}
