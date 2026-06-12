package com.roma.kai.ui.kai;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.remote.RetrofitClient;
import com.roma.kai.data.repository.KaiRepository;
import com.roma.kai.model.dto.KaiDashboarResponse;
import com.roma.kai.utils.Event;
import com.roma.kai.utils.UiMessage;

public class TuKaiViewModel extends AndroidViewModel {
    private final KaiRepository kaiRepository;
    private final MutableLiveData<TuKaiUiState> tuKaiUiState = new MutableLiveData<>();
    private final MutableLiveData<Event<UiMessage>> eventUiMessage = new MutableLiveData<>();


    public TuKaiViewModel(@NonNull Application application) {
        super(application);
        kaiRepository = new KaiRepository(RetrofitClient.getService(application));
    }

    public LiveData<TuKaiUiState> getTuKaiUiState() { return tuKaiUiState; }
    public LiveData<Event<UiMessage>> getEventUiMessage() { return eventUiMessage; }

    public void loadTuKaiData() {
        tuKaiUiState.setValue(TuKaiUiState.loading());

        kaiRepository.loadTuKaiView(new RepositoryCallback<KaiDashboarResponse>() {
            @Override
            public void onSuccess(KaiDashboarResponse data) {
                tuKaiUiState.setValue(new TuKaiUiState(
                        false,
                        true,
                        data.getEstadoKai(),
                        data.getMensajeEmocional(),
                        data.getAtributos(),
                        data.getCategoriaDominante(),
                        data.getCategoriaMenosDominante(),
                        data.getProgresoDiario()
                ));

            }

            @Override
            public void onError(String error) {
                tuKaiUiState.setValue(TuKaiUiState.error());
                eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
