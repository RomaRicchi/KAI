package com.roma.kai.ui.login;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.repository.AuthRepository;
import com.roma.kai.model.request.ForgotPasswordRequest;
import com.roma.kai.model.request.ResetPasswordRequest;
import com.roma.kai.data.remote.RetrofitClient;
import com.roma.kai.session.SessionManager;
import com.roma.kai.utils.Event;
import com.roma.kai.utils.UiMessage;

public class ForgotPasswordViewModel extends AndroidViewModel {
    private final AuthRepository authRepository;
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<Event<UiMessage>> eventUiMessage = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> codeSent = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> passwordResetSuccess = new MutableLiveData<>();

    public ForgotPasswordViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(
                SessionManager.getInstance(application),
                RetrofitClient.getService(application)
        );
    }

    public LiveData<Boolean> getLoading() { return loading; }
    public LiveData<Event<UiMessage>> getEventUiMessage() { return eventUiMessage; }
    public LiveData<Event<Boolean>> getCodeSent() { return codeSent; }
    public LiveData<Event<Boolean>> getPasswordResetSuccess() { return passwordResetSuccess; }

    public void requestResetCode(String email) {
        if (email == null || email.trim().isEmpty()) {
            eventUiMessage.setValue(new Event<>(new UiMessage("Ingrese su email", UiMessage.Type.ERROR)));
            return;
        }

        loading.setValue(true);
        authRepository.forgotPassword(new ForgotPasswordRequest(email), new RepositoryCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                loading.setValue(false);
                codeSent.setValue(new Event<>(true));
                eventUiMessage.setValue(new Event<>(new UiMessage("Código enviado al correo", UiMessage.Type.SUCCESS)));
            }

            @Override
            public void onError(String error) {
                loading.setValue(false);
                eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
            }
        });
    }

    public void resetPassword(String email, String code, String newPassword) {
        if (code == null || code.length() != 6) {
            eventUiMessage.setValue(new Event<>(new UiMessage("Código inválido", UiMessage.Type.ERROR)));
            return;
        }
        if (newPassword == null || newPassword.length() < 6) {
            eventUiMessage.setValue(new Event<>(new UiMessage("La contraseña debe tener al menos 6 caracteres", UiMessage.Type.ERROR)));
            return;
        }

        loading.setValue(true);
        authRepository.resetPassword(new ResetPasswordRequest(email, code, newPassword), new RepositoryCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                loading.setValue(false);
                passwordResetSuccess.setValue(new Event<>(true));
                eventUiMessage.setValue(new Event<>(new UiMessage("Contraseña restablecida con éxito", UiMessage.Type.SUCCESS)));
            }

            @Override
            public void onError(String error) {
                loading.setValue(false);
                eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
            }
        });
    }
}
