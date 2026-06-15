package com.roma.kai.ui.register;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.repository.AuthRepository;
import com.roma.kai.model.dto.AuthResponse;
import com.roma.kai.model.request.RegisterRequest;
import com.roma.kai.data.remote.RetrofitClient;
import com.roma.kai.session.SessionManager;
import com.roma.kai.utils.Event;
import com.roma.kai.utils.UiMessage;

public class RegisterViewModel extends AndroidViewModel {
    private final AuthRepository authRepository;
    private final MutableLiveData<RegisterUiState> uiState = new MutableLiveData<>();
    private final MutableLiveData<Event<UiMessage>> eventUiMessage = new MutableLiveData<>();

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(
                SessionManager.getInstance(application),
                RetrofitClient.getService(application)
        );
    }

    public LiveData<RegisterUiState> getUiState() { return uiState; }

    public LiveData<Event<UiMessage>> getEventUiMessage() { return eventUiMessage; }

    public void registrar(String nombre, String email, String password, String passwordConfirmed) {
        //validar y utilizar el EventUiMessage para mostrar si se necesita

        RegisterRequest registerRequest = new RegisterRequest(nombre, email, password);
        uiState.setValue(new RegisterUiState(true, false, null));

        authRepository.register(registerRequest, new RepositoryCallback<AuthResponse>() {
            @Override
            public void onSuccess(AuthResponse data) {
                uiState.setValue(new RegisterUiState(
                        false,
                        true,
                        data.getUsuario()
                ));
            }

            @Override
            public void onError(String error) {
                uiState.setValue(new RegisterUiState(
                        false,
                        false,
                        null
                ));
                eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
            }
        });
    }
}
