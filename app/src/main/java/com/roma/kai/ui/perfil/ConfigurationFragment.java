package com.roma.kai.ui.perfil;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.roma.kai.R;
import com.roma.kai.databinding.FragmentConfigurationBinding;
import com.roma.kai.model.entity.ConfiguracionUsuarioEntity;
import com.roma.kai.model.request.UpdateConfigurationRequest;
import com.roma.kai.utils.OnSafeClickListener;
import com.roma.kai.utils.UiMessage;
import com.roma.kai.utils.UiMessageHelper;

import java.util.Locale;

public class ConfigurationFragment extends Fragment {
    private FragmentConfigurationBinding binding;
    private ConfigurationViewModel viewModel;
    private String reminderTime;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConfigurationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ConfigurationViewModel.class);
        setupIntensityDropdown();
        setupListeners();
        setupObservers();
        viewModel.loadConfiguration();
    }

    private void setupIntensityDropdown() {
        String[] intensities = {"Baja", "Normal", "Alta"};
        binding.dropdownIntensity.setAdapter(new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                intensities
        ));
    }

    private void setupListeners() {
        binding.btnReminderTime.setOnClickListener(new OnSafeClickListener() {
            @Override
            public void onSafeClick(View v) {
                showTimePicker();
            }
        });
        binding.btnSaveConfiguration.setOnClickListener(new OnSafeClickListener() {
            @Override
            public void onSafeClick(View v) {
                saveConfiguration();
            }
        });
    }

    private void setupObservers() {
        viewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            if (state == null) return;
            boolean busy = state.isLoading() || state.isSaving();
            binding.progressBarConfiguration.setVisibility(busy ? View.VISIBLE : View.GONE);
            binding.btnSaveConfiguration.setEnabled(!busy);
            binding.layoutConfigurationContent.setAlpha(busy ? 0.65f : 1f);

            if (state.getConfiguration() != null) {
                binding.layoutConfigurationContent.setVisibility(View.VISIBLE);
                if (!state.isSaving()) {
                    renderConfiguration(state.getConfiguration());
                }
            } else {
                binding.layoutConfigurationContent.setVisibility(View.INVISIBLE);
            }
        });

        viewModel.getEventUiMessage().observe(getViewLifecycleOwner(), event -> {
            UiMessage message = event.obtenerContenidoSiNoManejado();
            if (message != null) {
                UiMessageHelper.showMessage(binding.getRoot(), requireContext(), message);
            }
        });
    }

    private void renderConfiguration(ConfiguracionUsuarioEntity configuration) {
        binding.switchNotifications.setChecked(configuration.isNotificacionesActivas());
        binding.switchSounds.setChecked(configuration.isSonidosActivos());
        binding.switchShowStreaks.setChecked(configuration.isMostrarRachas());
        binding.switchDiscreteMode.setChecked(configuration.isModoDiscreto());
        binding.switchLockPin.setChecked(configuration.isBloquearConPin());
        binding.switchEmotionalMessages.setChecked(configuration.isPermitirMensajesEmocionales());

        String intensity = configuration.getIntensidadKai();
        if (intensity == null || intensity.trim().isEmpty()) intensity = "normal";
        binding.dropdownIntensity.setText(capitalize(intensity), false);

        reminderTime = configuration.getHorarioRecordatorio();
        updateReminderButton();
    }

    private void showTimePicker() {
        int hour = 9;
        int minute = 0;
        if (reminderTime != null) {
            String[] parts = reminderTime.split(":");
            if (parts.length >= 2) {
                try {
                    hour = Integer.parseInt(parts[0]);
                    minute = Integer.parseInt(parts[1]);
                } catch (NumberFormatException ignored) {
                    hour = 9;
                    minute = 0;
                }
            }
        }

        new TimePickerDialog(requireContext(), (view, selectedHour, selectedMinute) -> {
            reminderTime = String.format(Locale.US, "%02d:%02d:00", selectedHour, selectedMinute);
            updateReminderButton();
        }, hour, minute, true).show();
    }

    private void updateReminderButton() {
        if (reminderTime == null || reminderTime.trim().isEmpty()) {
            binding.btnReminderTime.setText(R.string.config_reminder_time);
            return;
        }
        String visibleTime = reminderTime.length() >= 5 ? reminderTime.substring(0, 5) : reminderTime;
        binding.btnReminderTime.setText(getString(R.string.config_reminder_value, visibleTime));
    }

    private void saveConfiguration() {
        String intensity = binding.dropdownIntensity.getText().toString().trim().toLowerCase(Locale.ROOT);
        viewModel.saveConfiguration(new UpdateConfigurationRequest(
                binding.switchNotifications.isChecked(),
                binding.switchSounds.isChecked(),
                binding.switchShowStreaks.isChecked(),
                binding.switchDiscreteMode.isChecked(),
                intensity,
                reminderTime,
                binding.switchLockPin.isChecked(),
                binding.switchEmotionalMessages.isChecked()
        ));
    }

    private String capitalize(String value) {
        if (value == null || value.isEmpty()) return "";
        return value.substring(0, 1).toUpperCase(Locale.ROOT) + value.substring(1).toLowerCase(Locale.ROOT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
