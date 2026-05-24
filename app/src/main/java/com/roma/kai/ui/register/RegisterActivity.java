package com.roma.kai.ui.register;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.roma.kai.databinding.ActivityRegisterBinding;
import com.roma.kai.ui.main.MainActivity;
import com.roma.kai.ui.login.LoginActivity;

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

            if(uiState.isSuccess()) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                //mostrar msg
                finish();
            }
        });
    }

    private void setupListeners() {
        binding.btnCrearCuenta.setOnClickListener(v -> {
            registerVM.registrar(
                    binding.etNombre.getText().toString(),
                    binding.etEmailRegister.getText().toString(),
                    binding.etPasswordRegister.getText().toString(),
                    binding.etConfirmPassword.getText().toString()
            );
        });

        binding.btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}