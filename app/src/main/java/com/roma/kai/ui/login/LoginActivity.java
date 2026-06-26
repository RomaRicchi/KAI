package com.roma.kai.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.roma.kai.BuildConfig;
import com.roma.kai.databinding.ActivityLoginBinding;
import com.roma.kai.ui.main.MainActivity;
import com.roma.kai.ui.register.RegisterActivity;
import com.roma.kai.utils.UiMessage;
import com.roma.kai.utils.UiMessageHelper;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel loginVM;
    private CredentialManager credentialManager;
    private GetCredentialRequest googleRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginVM = new ViewModelProvider(this).get(LoginViewModel.class);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        credentialManager = CredentialManager.create(this);

        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                .setServerClientId(BuildConfig.GOOGLE_CLIENT_ID)
                .setFilterByAuthorizedAccounts(false)
                .build();

        googleRequest = new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();

        setupObservers();
        setupListeners();
    }

    private void setupObservers() {
        loginVM.getUiState().observe(this, loginUiState -> {
            binding.btnIniciarSesion.setEnabled(!loginUiState.isLoading());

            if(loginUiState.isSuccess()) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("messageTo", new UiMessage("Bienvenido", UiMessage.Type.SUCCESS));
                intent.putExtra("authUser", loginUiState.getAuthUser());
                startActivity(intent);
                finish();
            }
        });

        loginVM.getEventUiMessage().observe(this, eventUiMessage -> {
            UiMessage uiMessage = eventUiMessage.obtenerContenidoSiNoManejado();
            if(uiMessage == null) return;
            UiMessageHelper.showMessage(binding.getRoot(), LoginActivity.this, uiMessage);
        });
    }

    private void setupListeners() {
        binding.btnIniciarSesion.setOnClickListener(v -> {
            loginVM.login(
                    binding.etEmail.getText().toString(),
                    binding.etPassword.getText().toString()
            );
        });

        binding.txtForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        binding.btnRegistrarse.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        binding.btnGoogle.setOnClickListener(v -> {
            startGoogleLogin();
        });
    }

    private void startGoogleLogin() {

        credentialManager.getCredentialAsync(
                this,
                googleRequest,
                null,
                getMainExecutor(),
                new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {

                    @Override
                    public void onResult(GetCredentialResponse result) {
                        handleGoogleResult(result);
                    }

                    @Override
                    public void onError(GetCredentialException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private void handleGoogleResult(GetCredentialResponse result) {

        Credential credential = result.getCredential();

        if (!(credential instanceof CustomCredential)) {
            return;
        }

        CustomCredential customCredential = (CustomCredential) credential;

        if (!GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL.equals(customCredential.getType())) {
            return;
        }

        try {

            GoogleIdTokenCredential googleCredential = GoogleIdTokenCredential.createFrom(customCredential.getData());

            String idToken = googleCredential.getIdToken();

            loginVM.googleLogin(idToken);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}