package com.roma.kai.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.roma.kai.R;
import com.roma.kai.databinding.FragmentInicioBinding;
import com.roma.kai.model.entity.Habito;
import com.roma.kai.ui.habitos.HabitosAdapter;
import com.roma.kai.utils.UiMessage;
import com.roma.kai.utils.UiMessageHelper;

import java.util.ArrayList;
import java.util.List;

public class InicioFragment extends Fragment {
    private FragmentInicioBinding binding;
    private InicioViewModel inicioVM;
    private InicioHabitosAdapter habitosAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInicioBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inicioVM = new ViewModelProvider(this).get(InicioViewModel.class);

        setupRecyclerView();
        setupObservers();
        setupListeners();

        inicioVM.loadHome();
    }

    private void setupObservers() {
        inicioVM.getInicioUiState().observe(getViewLifecycleOwner(), inicioUiState -> {
            if(inicioUiState == null) return;

            //desarrollar
            if(inicioUiState.isSuccess()) {
                habitosAdapter.setHabitos(inicioUiState.getHabitosDiarios());
                binding.tvHomeXp.setText(inicioUiState.getXpTotal() + " XP");
                binding.tvHomeRacha.setText(inicioUiState.getRachaActual() + " Días");
            }
        });

        inicioVM.getEventUiMessage().observe(getViewLifecycleOwner(), eventUiMessage -> {
            UiMessage uiMessage = eventUiMessage.obtenerContenidoSiNoManejado();
            if(uiMessage == null) return;
            UiMessageHelper.showMessage(binding.getRoot(), requireContext(), uiMessage);
        });
    }

    private void setupListeners() {

    }

    private void setupRecyclerView() {
        habitosAdapter = new InicioHabitosAdapter();
        binding.rvHabitosHoy.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvHabitosHoy.setAdapter(habitosAdapter);
    }

    private void setupHabitosHoy() {
        List<Habito> habitosHoy = new ArrayList<>();
        habitosHoy.add(new Habito("Tomar Agua", "Vitalidad", 10, 6, true, R.drawable.ic_gallery_black_24dp));
        habitosHoy.add(new Habito("Leer", "Mente", 20, 5, false, R.drawable.ic_gallery_black_24dp));

        // Ya no navegamos al detalle desde el inicio por pedido del usuario
        HabitosAdapter adapter = new HabitosAdapter(habitosHoy, habito -> {
            // Acción desactivada o podrías implementar un check rápido aquí
        });

        binding.rvHabitosHoy.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvHabitosHoy.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
