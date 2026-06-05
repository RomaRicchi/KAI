package com.roma.kai.ui.habitos.detalleHabito;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.roma.kai.utils.ImageUi;
import com.roma.kai.utils.UiMessage;
import com.roma.kai.utils.UiMessageHelper;

import java.util.List;

public class DetalleHabitoFragment extends Fragment {

    private FragmentDetalleHabitoBinding binding;
    private DetalleHabitoViewModel viewModel;
    private String habitUserId;

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

            // 1. Carga inicial (Pantalla vacía)
            boolean isInitialLoading = state.isLoading() && (state.getHabitName() == null || state.getHabitName().isEmpty());
            binding.progressDetalle.setVisibility(isInitialLoading ? View.VISIBLE : View.GONE);
            binding.scrollDetalleHabito.setVisibility(isInitialLoading ? View.INVISIBLE : View.VISIBLE);

            // 2. Feedback en el botón (Carga de acción)
            boolean isActionLoading = state.isLoading() && !isInitialLoading;
            binding.progressBtnCompletar.setVisibility(isActionLoading ? View.VISIBLE : View.GONE);
            binding.btnCompletarHoyDetalle.setEnabled(!state.isLoading() && !state.isTodayCompleted());
            
            // Ocultar texto/icono del botón mientras carga para que se vea el spinner
            binding.btnCompletarHoyDetalle.setAlpha(isActionLoading ? 0f : 1.0f);

            if (!isInitialLoading) {
                bindStateToUi(state);
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

    private void bindStateToUi(DetalleHabitoUiState state) {
        binding.txtDetalleNombre.setText(state.getHabitName());
        binding.txtDetalleCategoria.setText(state.getCategoryName());
        binding.txtDetalleRacha.setText(getString(R.string.formato_dias, Integer.parseInt(state.getCurrentStreak())));
        binding.txtDetalleExitosos.setText(getString(R.string.formato_dias, Integer.parseInt(state.getSuccessfulDaysCount())));

        // Icono
        Glide.with(this).load(ImageUi.getDrawable(state.getCategoryImageKey())).into(binding.imgDetalleIcon);

        // Header Calendario
        binding.txtDetalleMesAnio.setText(state.getMonthYearHeader());

        // Calendario Grid
        renderCalendarGrid(state.getCalendarDays());

        // Botón Completar Hoy
        if (state.isTodayCompleted()) {
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

    private void renderCalendarGrid(List<CalendarDayUiModel> days) {
        binding.gridDetalleCalendario.removeAllViews();
        if (days == null) return;

        for (CalendarDayUiModel day : days) {
            if (day.isEmpty()) {
                View empty = new View(getContext());
                android.widget.GridLayout.LayoutParams params = new android.widget.GridLayout.LayoutParams();
                params.width = 0;
                params.height = 1;
                params.columnSpec = android.widget.GridLayout.spec(android.widget.GridLayout.UNDEFINED, 1f);
                empty.setLayoutParams(params);
                binding.gridDetalleCalendario.addView(empty);
            } else {
                View container = LayoutInflater.from(getContext()).inflate(R.layout.item_calendar_dot, binding.gridDetalleCalendario, false);
                TextView txtDay = container.findViewById(R.id.txt_day_number);

                txtDay.setText(day.getDayNumber());

                if (day.isCompleted()) {
                    txtDay.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.kai_primary_selected));
                    txtDay.setTextColor(ContextCompat.getColor(requireContext(), R.color.kai_primary));
                } else {
                    txtDay.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.kai_primary_very_soft));
                    if (day.isToday()) {
                        txtDay.setTextColor(ContextCompat.getColor(requireContext(), R.color.kai_primary));
                        txtDay.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.white));
                    } else {
                        txtDay.setTextColor(ContextCompat.getColor(requireContext(), R.color.kai_text_secondary));
                    }
                }
                binding.gridDetalleCalendario.addView(container);
            }
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

        binding.btnPrevWeek.setOnClickListener(v -> viewModel.moveMonth(-1));
        binding.btnNextWeek.setOnClickListener(v -> viewModel.moveMonth(1));

        binding.btnCompletarHoyDetalle.setOnClickListener(v -> {
            if (habitUserId != null) {
                viewModel.completeHabit(habitUserId);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
