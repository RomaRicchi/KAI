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
    
    public void loadHomeView() {
        inicioUiState.setValue(InicioUiState.loading());

        inicioRepository.loadHomeView(new RepositoryCallback<HomeResponse>() {
            @Override
            public void onSuccess(HomeResponse data) {
                String xp = data.getXpTotal() + " XP";
                String racha = data.getRachaActual() + " Días";


                inicioUiState.setValue(new InicioUiState(
                        false,
                        true,
                        data.getEstadoKai(),
                        data.getMensajeMotivacional(),
                        xp,
                        racha,
                        data.getHabitosDiarios()
                ));
                
            }

            @Override
            public void onError(String error) {
                inicioUiState.setValue(InicioUiState.error());
                eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
