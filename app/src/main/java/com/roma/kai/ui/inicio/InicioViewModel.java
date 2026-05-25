package com.roma.kai.ui.inicio;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.remote.RetrofitClient;
import com.roma.kai.data.repository.InicioRepository;
import com.roma.kai.model.dto.HomeResponse;
import com.roma.kai.utils.Event;
import com.roma.kai.utils.UiMessage;

import java.util.Collections;

public class InicioViewModel extends AndroidViewModel {
    private final InicioRepository inicioRepository;
    private final MutableLiveData<InicioUiState> inicioUiState = new MutableLiveData<>();
    private final MutableLiveData<Event<UiMessage>> eventUiMessage = new MutableLiveData<>();
    public InicioViewModel(@NonNull Application application) {
        super(application);
        inicioRepository = new InicioRepository(RetrofitClient.getService(application));
    }

    public LiveData<InicioUiState> getInicioUiState() { return inicioUiState; }

    public LiveData<Event<UiMessage>> getEventUiMessage() { return eventUiMessage; }

    public void loadHome() {
        inicioUiState.setValue(new InicioUiState(
                true,
                false,
                null,
                "",
                0,
                0,
                Collections.emptyList(),
                null
        ));
        inicioRepository.loadHome(new RepositoryCallback<HomeResponse>() {
            @Override
            public void onSuccess(HomeResponse data) {
                inicioUiState.setValue(new InicioUiState(
                        false,
                        true,
                        data.getEstadoKai(),
                        data.getMensajeMotivacional(),
                        data.getXpTotal(),
                        data.getRachaActual(),
                        data.getHabitosDiarios(),
                        data.getProgresoDiario()
                ));
            }

            @Override
            public void onError(String error) {
                inicioUiState.setValue(new InicioUiState(
                        false,
                        false,
                        null,
                        "",
                        0,
                        0,
                        Collections.emptyList(),
                        null
                ));
                eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
            }
        });
    }
}
