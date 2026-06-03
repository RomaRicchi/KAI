package com.roma.kai.ui.habitos.detalleHabito;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.roma.kai.R;
import com.roma.kai.databinding.FragmentDetalleHabitoBinding;
import com.roma.kai.model.dto.HabitDetail;
import com.roma.kai.model.dto.HabitDetailResponse;
import com.roma.kai.model.dto.HabitRecord;
import com.roma.kai.utils.ImageUi;
import com.roma.kai.utils.UiMessage;
import com.roma.kai.utils.UiMessageHelper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DetalleHabitoFragment extends Fragment {

    private FragmentDetalleHabitoBinding binding;
    private DetalleHabitoViewModel viewModel;
    private String habitUserId;
    private LocalDate currentViewDate = LocalDate.now();
    private Map<String, Boolean> habitRecords = new HashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            habitUserId = getArguments().getString("habitoId");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetalleHabitoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DetalleHabitoViewModel.class);

        setupObservers();
        setupListeners();

        if (habitUserId != null) {
            viewModel.loadHabitDetail(habitUserId);
        }
    }

    private void setupObservers() {
        viewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            if (state == null) return;

            // Gestión de carga
            if (state.isLoading()) {
                binding.progressDetalle.setVisibility(View.VISIBLE);
                binding.scrollDetalleHabito.setVisibility(View.INVISIBLE);
            } else {
                binding.progressDetalle.setVisibility(View.GONE);
                if (state.getHabit() != null) {
                    binding.scrollDetalleHabito.setVisibility(View.VISIBLE);
                    populateData(state.getHabit());
                }
            }

            if (state.isDeactivated()) {
                android.widget.Toast.makeText(requireContext(), "Hábito desactivado correctamente", android.widget.Toast.LENGTH_SHORT).show();
                Navigation.findNavController(requireView()).popBackStack();
            }
        });

        viewModel.getEventUiMessage().observe(getViewLifecycleOwner(), event -> {
            UiMessage message = event.obtenerContenidoSiNoManejado();
            if (message != null) {
                UiMessageHelper.showMessage(binding.getRoot(), requireContext(), message);
            }
        });
    }

    private void populateData(HabitDetailResponse response) {
        HabitDetail habit = response.getHabit();
        List<HabitRecord> records = response.getRegistros();

        binding.txtDetalleNombre.setText(habit.getNombre());
        binding.txtDetalleCategoria.setText(habit.getCategoria());
        binding.txtDetalleRacha.setText(getString(R.string.formato_dias, habit.getRachaActual()));

        long completedCount = 0;
        habitRecords.clear();
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (records != null) {
            for (HabitRecord record : records) {
                if (record.isCompletado()) completedCount++;
            String dateKey = record.getFecha();
                if (dateKey != null && !dateKey.isEmpty()) {
                    // Si la fecha incluye la hora (T00:00:00Z), nos quedamos solo con la parte de la fecha
                    if (dateKey.contains("T")) {
                        dateKey = dateKey.split("T")[0];
                    }
                    habitRecords.put(dateKey, record.isCompletado());
                }
            }
        }
        binding.txtDetalleExitosos.setText(getString(R.string.formato_dias, (int) completedCount));

        // Icono
        String categoryKey = habit.getCategoria();
        String imgData = habit.getImagenHabito();
        
        if (imgData != null && imgData.startsWith("http")) {
            Glide.with(this).load(imgData).placeholder(R.drawable.ic_gallery_black_24dp).into(binding.imgDetalleIcon);
        } else {
            // Usamos el resolver con el nombre de la categoria para consistencia
            Glide.with(this).load(ImageUi.getDrawable(categoryKey)).into(binding.imgDetalleIcon);
        }

        setupMonthlyCalendar();

        // Botón Completar Hoy
        LocalDate today = LocalDate.now();
        String todayStr = today.format(parser);
        boolean completadoHoy = Boolean.TRUE.equals(habitRecords.get(todayStr));

        if (completadoHoy) {
            binding.btnCompletarHoyDetalle.setIconResource(R.drawable.icons8_check);
            binding.btnCompletarHoyDetalle.setIconTint(ContextCompat.getColorStateList(requireContext(), R.color.kai_primary));
            binding.btnCompletarHoyDetalle.setEnabled(false);
            binding.btnCompletarHoyDetalle.setText("Completado");
            binding.btnCompletarHoyDetalle.setStrokeColor(ContextCompat.getColorStateList(requireContext(), R.color.kai_primary_selected));
        } else {
            binding.btnCompletarHoyDetalle.setIconResource(R.drawable.circle_dot);
            binding.btnCompletarHoyDetalle.setIconTint(ContextCompat.getColorStateList(requireContext(), R.color.divider_color));
            binding.btnCompletarHoyDetalle.setEnabled(true);
            binding.btnCompletarHoyDetalle.setText("Completar hoy");
            binding.btnCompletarHoyDetalle.setStrokeColor(ContextCompat.getColorStateList(requireContext(), R.color.divider_color));
        }
    }

    private void setupMonthlyCalendar() {
        binding.gridDetalleCalendario.removeAllViews();
        
        // Header Mes y Año
        String monthName = currentViewDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        String headerText = monthName.substring(0, 1).toUpperCase() + monthName.substring(1) + " " + currentViewDate.getYear();
        binding.txtDetalleMesAnio.setText(headerText);

        LocalDate firstOfMonth = currentViewDate.withDayOfMonth(1);
        int daysInMonth = currentViewDate.lengthOfMonth();
        
        // El valor de DayOfWeek va de 1 (Lunes) a 7 (Domingo)
        int offset = firstOfMonth.getDayOfWeek().getValue() - 1;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();

        // Espacios vacíos para el offset inicial
        for (int i = 0; i < offset; i++) {
            View empty = new View(getContext());
            android.widget.GridLayout.LayoutParams params = new android.widget.GridLayout.LayoutParams();
            params.width = 0;
            params.height = 1; 
            params.columnSpec = android.widget.GridLayout.spec(android.widget.GridLayout.UNDEFINED, 1f);
            empty.setLayoutParams(params);
            binding.gridDetalleCalendario.addView(empty);
        }

        // Días del mes
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = currentViewDate.withDayOfMonth(day);
            String dateStr = date.format(formatter);
            
            View container = LayoutInflater.from(getContext()).inflate(R.layout.item_calendar_dot, binding.gridDetalleCalendario, false);
            TextView txtDay = container.findViewById(R.id.txt_day_number);

            txtDay.setText(String.valueOf(day));
            
            Boolean completado = habitRecords.get(dateStr);
            if (completado != null && completado) {
                // Si está realizado, pintamos el fondo del número de un verde clarito
                txtDay.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.kai_primary_selected));
                txtDay.setTextColor(ContextCompat.getColor(requireContext(), R.color.kai_primary));
            } else {
                txtDay.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.kai_primary_very_soft));
                // Si es hoy pero no completado, resaltamos el texto
                if (date.equals(today)) {
                    txtDay.setTextColor(ContextCompat.getColor(requireContext(), R.color.kai_primary));
                    txtDay.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.white));
                } else {
                    txtDay.setTextColor(ContextCompat.getColor(requireContext(), R.color.kai_text_secondary));
                }
            }
            
            binding.gridDetalleCalendario.addView(container);
        }
    }

    private void setupListeners() {
        binding.btnDarBajaHabito.setOnClickListener(v -> {
            if (habitUserId != null) {
                new com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
                        .setTitle("¿Dar de baja hábito?")
                        .setMessage("Esta acción desactivará el hábito y ya no aparecerá en tu lista diaria.")
                        .setPositiveButton("Confirmar", (dialog, which) -> {
                            viewModel.deactivateHabit(habitUserId);
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });

        binding.btnVerCatalogoDesdeDetalle.setOnClickListener(v -> 
            Navigation.findNavController(v).navigate(R.id.action_nav_detalle_habito_to_nav_seleccion_habitos)
        );

        binding.btnCompletarHoyDetalle.setOnClickListener(v -> {
            if (habitUserId != null) {
                viewModel.completeHabit(habitUserId);
            }
        });

        binding.btnPrevWeek.setOnClickListener(v -> {
            currentViewDate = currentViewDate.minusMonths(1);
            setupMonthlyCalendar();
        });

        binding.btnNextWeek.setOnClickListener(v -> {
            currentViewDate = currentViewDate.plusMonths(1);
            setupMonthlyCalendar();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
