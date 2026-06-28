package com.roma.kai.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.roma.kai.databinding.ActivityRegisterBinding;
import com.roma.kai.ui.main.MainActivity;
import com.roma.kai.ui.login.LoginActivity;
import com.roma.kai.utils.OnSafeClickListener;
import com.roma.kai.utils.UiMessage;
import com.roma.kai.utils.UiMessageHelper;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private RegisterViewModel registerVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        registerVM = new ViewModelProvider(this).get(RegisterViewModel.class);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupObservers();
        setupListeners();
    }

    private void setupObservers()  {
        registerVM.getUiState().observe(this, uiState -> {
            binding.btnCrearCuenta.setEnabled(!uiState.isLoading());

            // Mostrar errores en los campos correspondientes
            binding.tilNombre.setError(uiState.getNameError());
            binding.tilUsername.setError(uiState.getUsernameError());
            binding.tilEmail.setError(uiState.getEmailError());
            binding.tilPassword.setError(uiState.getPasswordError());
            binding.tilConfirmPassword.setError(uiState.getConfirmPasswordError());

            if(uiState.isSuccess()) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.putExtra("messageTo", new UiMessage("Bienvenido", UiMessage.Type.SUCCESS));
                intent.putExtra("authUser", uiState.getAuthUser());
                startActivity(intent);
                finish();
            }
        });

        registerVM.getEventUiMessage().observe(this, eventUiMessage -> {
            UiMessage uiMessage = eventUiMessage.obtenerContenidoSiNoManejado();
            if(uiMessage == null) return;
            UiMessageHelper.showMessage(binding.getRoot(), RegisterActivity.this, uiMessage);
        });
    }

    private void setupListeners() {
        setSupportActionBar(binding.toolbarRegister);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        binding.toolbarRegister.setNavigationOnClickListener(new OnSafeClickListener() {
            @Override
            public void onSafeClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.btnCrearCuenta.setOnClickListener(new OnSafeClickListener() {
            @Override
            public void onSafeClick(View v) {
                registerVM.registrar(
                        binding.etNombre.getText().toString(),
                        binding.etUsername.getText().toString(),
                        binding.etEmailRegister.getText().toString(),
                        binding.etPasswordRegister.getText().toString(),
                        binding.etConfirmPassword.getText().toString()
                );
            }
        });

        binding.txtBackToLogin.setOnClickListener(new OnSafeClickListener() {
            @Override
            public void onSafeClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
