package com.roma.kai.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.roma.kai.databinding.ActivityLoginBinding;
import com.roma.kai.ui.main.MainActivity;
import com.roma.kai.ui.register.RegisterActivity;
import com.roma.kai.utils.UiMessage;
import com.roma.kai.utils.UiMessageHelper;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel loginVM;

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

        setupObservers();
        setupListeners();
    }

    private void setupObservers() {
        loginVM.getUiState().observe(this, loginUiState -> {
            binding.btnIniciarSesion.setEnabled(!loginUiState.isLoading());

            if(loginUiState.isSuccess()) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("messageTo", new UiMessage("Bienvenido", UiMessage.Type.SUCCESS));
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

        binding.btnRegistrarse.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}