package com.roma.kai.ui.habitos.detalleHabito;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.remote.RetrofitClient;
import com.roma.kai.data.repository.HabitosRepository;
import com.roma.kai.model.dto.HabitDetailResponse;
import com.roma.kai.utils.Event;
import com.roma.kai.utils.UiMessage;

public class DetalleHabitoViewModel extends AndroidViewModel {
    private final HabitosRepository habitosRepository;
    private final MutableLiveData<DetalleHabitoUiState> uiState = new MutableLiveData<>();
    private final MutableLiveData<Event<UiMessage>> eventUiMessage = new MutableLiveData<>();

    public DetalleHabitoViewModel(@NonNull Application application) {
        super(application);
        habitosRepository = new HabitosRepository(RetrofitClient.getService(application));
    }

    public LiveData<DetalleHabitoUiState> getUiState() { return uiState; }
    public LiveData<Event<UiMessage>> getEventUiMessage() { return eventUiMessage; }

    public void loadHabitDetail(String habitUserId) {
        uiState.setValue(new DetalleHabitoUiState(true, null, null, false));

        habitosRepository.getHabitDetail(habitUserId, new RepositoryCallback<HabitDetailResponse>() {
            @Override
            public void onSuccess(HabitDetailResponse data) {
                uiState.setValue(new DetalleHabitoUiState(false, data, null, false));
            }

            @Override
            public void onError(String error) {
                uiState.setValue(new DetalleHabitoUiState(false, null, error, false));
                eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
            }
        });
    }

    public void deactivateHabit(String habitUserId) {
        HabitDetailResponse currentHabit = uiState.getValue() != null ? uiState.getValue().getHabit() : null;
        uiState.setValue(new DetalleHabitoUiState(true, currentHabit, null, false));

        habitosRepository.deactivateHabit(habitUserId, new RepositoryCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                uiState.setValue(new DetalleHabitoUiState(false, currentHabit, null, true));
                eventUiMessage.setValue(new Event<>(new UiMessage("Hábito desactivado", UiMessage.Type.SUCCESS)));
            }

            @Override
            public void onError(String error) {
                uiState.setValue(new DetalleHabitoUiState(false, currentHabit, error, false));
                eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
            }
        });
    }

    public void completeHabit(String habitUserId) {
        HabitDetailResponse currentHabit = uiState.getValue() != null ? uiState.getValue().getHabit() : null;
        uiState.setValue(new DetalleHabitoUiState(true, currentHabit, null, false));

        habitosRepository.completeHabit(habitUserId, new RepositoryCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                // Volvemos a cargar para refrescar calendario y racha
                loadHabitDetail(habitUserId);
                eventUiMessage.setValue(new Event<>(new UiMessage("¡Hábito completado!", UiMessage.Type.SUCCESS)));
            }

            @Override
            public void onError(String error) {
                uiState.setValue(new DetalleHabitoUiState(false, currentHabit, error, false));
                eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
            }
        });
    }
}
