package com.roma.kai.ui.habitos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.roma.kai.R;
import com.roma.kai.databinding.FragmentHabitosBinding;
import com.roma.kai.model.entity.Habito;
import com.roma.kai.utils.UiMessage;
import com.roma.kai.utils.UiMessageHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HabitosFragment extends Fragment {

    private FragmentHabitosBinding binding;
    private HabitosViewModel habitosVM;
    private HabitosAdapter habitosAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHabitosBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        habitosVM = new ViewModelProvider(this).get(HabitosViewModel.class);

        binding.fabAddHabito.setOnClickListener(v -> 
            Navigation.findNavController(v).navigate(R.id.action_nav_habitos_to_nav_seleccion_habitos)
        );

        setupRecyclerView();
        setupObservers();

        habitosVM.loadHabitosView();
    }

    private void setupRecyclerView() {
        habitosAdapter = new HabitosAdapter(habito -> {
            // Al hacer clic en un hábito, navegamos al detalle
            Bundle bundle = new Bundle();
            bundle.putString("habitoId", habito.getHabitoUsuarioId());
            Navigation.findNavController(requireView()).navigate(R.id.action_nav_habitos_to_nav_detalle_habito, bundle);
        });

        binding.rvMisHabitos.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvMisHabitos.setAdapter(habitosAdapter);
    }

    private void setupObservers() {
        habitosVM.getHabitosUiState().observe(getViewLifecycleOwner(), habitosUiState -> {
            if(habitosUiState == null) return;

            // Mostrar u ocultar ProgressBar y Contenido
            if (habitosUiState.isLoading()) {
                binding.progressBarHabitos.setVisibility(View.VISIBLE);
                binding.layoutHabitosContent.setVisibility(View.INVISIBLE);
            } else {
                binding.progressBarHabitos.setVisibility(View.GONE);
                binding.layoutHabitosContent.setVisibility(View.VISIBLE);
            }

            if(habitosUiState.isSuccess()) {
                if (habitosUiState.getHabitosUsuario() != null && !habitosUiState.getHabitosUsuario().isEmpty()) {
                    binding.rvMisHabitos.setVisibility(View.VISIBLE);
                    binding.layoutEmptyHabitos.setVisibility(View.GONE);
                    habitosAdapter.submitList(habitosUiState.getHabitosUsuario());
                } else {
                    binding.rvMisHabitos.setVisibility(View.GONE);
                    binding.layoutEmptyHabitos.setVisibility(View.VISIBLE);
                }
            }
        });
        habitosVM.getEventUiMessage().observe(
                getViewLifecycleOwner(),
                event -> {

                    if(event == null) return;

                    UiMessage message =
                            event.obtenerContenidoSiNoManejado();

                    if(message != null) {

                        UiMessageHelper.showMessage(
                                binding.getRoot(),
                                requireContext(),
                                message
                        );
                    }
                }
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        if (habitosVM != null) {
            habitosVM.loadHabitosView();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
