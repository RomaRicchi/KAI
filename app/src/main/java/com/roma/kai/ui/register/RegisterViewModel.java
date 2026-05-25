package com.roma.kai.ui.register;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.repository.AuthRepository;
import com.roma.kai.model.dto.TokenDto;
import com.roma.kai.model.request.RegisterRequest;
import com.roma.kai.model.response.ResponseData;
import com.roma.kai.data.remote.ApiService;
import com.roma.kai.data.remote.RetrofitClient;
import com.roma.kai.session.SessionManager;
import com.roma.kai.utils.Event;
import com.roma.kai.utils.UiMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        uiState.setValue(new RegisterUiState(true, false));

        authRepository.register(registerRequest, new RepositoryCallback<TokenDto>() {
            @Override
            public void onSuccess(TokenDto data) {
                uiState.setValue(new RegisterUiState(
                        true, // para que no desactive el boton antes de enviar al home
                        true
                ));
            }

            @Override
            public void onError(String error) {
                uiState.setValue(new RegisterUiState(
                        false,
                        false
                ));
                eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
            }
        });
    }
}
