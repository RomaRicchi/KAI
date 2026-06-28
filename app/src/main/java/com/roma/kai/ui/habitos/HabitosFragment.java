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
import com.roma.kai.utils.OnSafeClickListener;
import com.roma.kai.utils.UiMessage;
import com.roma.kai.utils.UiMessageHelper;

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

        binding.fabAddHabito.setOnClickListener(new OnSafeClickListener() {
            @Override
            public void onSafeClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_habitos_to_nav_seleccion_habitos);
            }
        });

        setupRecyclerView();
        setupObservers();

        habitosVM.loadHabitosView();
    }

    private void setupObservers() {
        habitosVM.getHabitosUiState().observe(getViewLifecycleOwner(), state -> {
            if (state == null) return;

            // Loading state
            binding.progressBarHabitos.setVisibility(state.isLoading() ? View.VISIBLE : View.GONE);
            binding.layoutHabitosContent.setVisibility(state.isLoading() ? View.INVISIBLE : View.VISIBLE);

            if (state.isSuccess()) {
                binding.rvMisHabitos.setVisibility(state.isEmpty() ? View.GONE : View.VISIBLE);
                binding.layoutEmptyHabitos.setVisibility(state.isEmpty() ? View.VISIBLE : View.GONE);
                
                if (!state.isEmpty()) {
                    habitosAdapter.submitList(state.getHabitosUsuario());
                }
            }
        });

        habitosVM.getEventUiMessage().observe(getViewLifecycleOwner(), event -> {
            UiMessage message = event.obtenerContenidoSiNoManejado();
            if (message != null) {
                UiMessageHelper.showMessage(binding.getRoot(), requireContext(), message);
            }
        });
    }

    private void setupRecyclerView() {
        habitosAdapter = new HabitosAdapter(habito -> {
            Bundle bundle = new Bundle();
            bundle.putString("habitoId", habito.getHabitoUsuarioId());
            Navigation.findNavController(requireView()).navigate(R.id.action_nav_habitos_to_nav_detalle_habito, bundle);
        });

        binding.rvMisHabitos.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvMisHabitos.setAdapter(habitosAdapter);
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
