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

    public void registrar(String nombre, String username, String email, String password, String passwordConfirmed) {
        boolean hasError = false;
        String nameError = null;
        String usernameError = null;
        String emailError = null;
        String passwordError = null;
        String confirmPasswordError = null;

        if (nombre == null || nombre.trim().isEmpty()) {
            nameError = "El nombre es obligatorio";
            hasError = true;
        }

        if (username == null || username.trim().isEmpty()) {
            usernameError = "El nombre de usuario es obligatorio";
            hasError = true;
        } else if (username.length() < 3) {
            usernameError = "El nombre de usuario debe tener al menos 3 caracteres";
            hasError = true;
        }

        if (email == null || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Ingrese un correo válido";
            hasError = true;
        }

        if (password == null || password.length() < 6) {
            passwordError = "La contraseña debe tener al menos 6 caracteres";
            hasError = true;
        }

        if (passwordConfirmed == null || !passwordConfirmed.equals(password)) {
            confirmPasswordError = "Las contraseñas no coinciden";
            hasError = true;
        }

        if (hasError) {
            uiState.setValue(RegisterUiState.error(nameError, usernameError, emailError, passwordError, confirmPasswordError));
            return;
        }

        RegisterRequest registerRequest = new RegisterRequest(nombre, username, email, password);
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
