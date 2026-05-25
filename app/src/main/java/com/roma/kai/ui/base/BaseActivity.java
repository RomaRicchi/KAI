package com.roma.kai.ui.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.roma.kai.R;
import com.roma.kai.databinding.ActivityBaseBinding;
import com.roma.kai.ui.main.MainActivity;
import com.roma.kai.ui.login.LoginActivity;

public class BaseActivity extends AppCompatActivity {
    private ActivityBaseBinding binding;
    private BaseViewModel baseVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityBaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        baseVM = new ViewModelProvider(this).get(BaseViewModel.class);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupObservers();
        
        // Iniciamos la verificación de sesión
        baseVM.verificarSession();
    }

    private void setupObservers() {
        baseVM.getNavigateToHome().observe(this, navigate -> {
            if (navigate) {
                startActivity(new Intent(BaseActivity.this, MainActivity.class));
                finish(); // Cerramos la pantalla base para que no pueda volver atrás
            }
        });

        baseVM.getNavigateToLogin().observe(this, navigate -> {
            if (navigate) {
                startActivity(new Intent(BaseActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
