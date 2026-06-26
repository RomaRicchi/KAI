package com.roma.kai.ui.login;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.roma.kai.databinding.ActivityForgotPasswordBinding;
import com.roma.kai.utils.UiMessage;
import com.roma.kai.utils.UiMessageHelper;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ActivityForgotPasswordBinding binding;
    private ForgotPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);

        setupListeners();
        setupObservers();
    }

    private void setupListeners() {
        binding.toolbarForgot.setNavigationOnClickListener(v -> finish());

        binding.btnSendCode.setOnClickListener(v -> {
            String email = binding.etEmailForgot.getText().toString();
            viewModel.requestResetCode(email);
        });

        binding.btnResetPassword.setOnClickListener(v -> {
            String email = binding.etEmailForgot.getText().toString();
            String code = binding.etCode.getText().toString();
            String password = binding.etNewPassword.getText().toString();
            viewModel.resetPassword(email, code, password);
        });
    }

    private void setupObservers() {
        viewModel.getLoading().observe(this, isLoading -> {
            binding.progressForgot.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            binding.btnSendCode.setEnabled(!isLoading);
            binding.btnResetPassword.setEnabled(!isLoading);
        });

        viewModel.getCodeSent().observe(this, event -> {
            if (event.obtenerContenidoSiNoManejado() != null) {
                binding.layoutStep1.setVisibility(View.GONE);
                binding.layoutStep2.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getPasswordResetSuccess().observe(this, event -> {
            if (event.obtenerContenidoSiNoManejado() != null) {
                finish();
            }
        });

        viewModel.getEventUiMessage().observe(this, event -> {
            UiMessage message = event.obtenerContenidoSiNoManejado();
            if (message != null) {
                UiMessageHelper.showMessage(binding.getRoot(), this, message);
            }
        });
    }
}
