package com.roma.kai.ui.inicio;

import android.media.MediaPlayer;
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
import com.roma.kai.databinding.FragmentInicioBinding;
import com.roma.kai.utils.ImageUi;
import com.roma.kai.utils.UiMessage;
import com.roma.kai.utils.UiMessageHelper;

public class InicioFragment extends Fragment {
    private FragmentInicioBinding binding;
    private InicioViewModel inicioVM;
    private InicioHabitosAdapter habitosAdapter;
    private MediaPlayer mediaPlayer;

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

        inicioVM.loadHomeView();
    }

    private void playSound() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.prueba1);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupObservers() {
        // Observamos el estado general de la UI
        inicioVM.getInicioUiState().observe(getViewLifecycleOwner(), state -> {
            if (state == null) return;

            binding.progressBarHome.setVisibility(state.isLoading() ? View.VISIBLE : View.GONE);
            binding.layoutHomeContent.setVisibility(state.isLoading() ? View.INVISIBLE : View.VISIBLE);

            if (state.isSuccess()) {
                habitosAdapter.submitList(state.getHabitosDiarios());
                binding.tvHomeXp.setText(state.getFormattedXp());
                binding.tvHomeRacha.setText(state.getFormattedRacha());
                
                if (state.getMensajeMotivacional() != null && !state.getMensajeMotivacional().isEmpty()) {
                    binding.cardMessage.setVisibility(View.VISIBLE);
                    binding.tvMotivationalMessage.setText(state.getMensajeMotivacional());
                } else {
                    binding.cardMessage.setVisibility(View.GONE);
                }

                binding.kaiView.setAnimation(state.getEstadoKai().getEtapaActual(), "enojado");
                binding.kaiView.startAnimation();
            }
        });

        inicioVM.getEventUiMessage().observe(getViewLifecycleOwner(), event -> {
            UiMessage message = event.obtenerContenidoSiNoManejado();
            if (message != null) {
                UiMessageHelper.showMessage(binding.getRoot(), requireContext(), message);
            }
        });
    }

    private void setupRecyclerView() {
        habitosAdapter = new InicioHabitosAdapter(habito -> {
            Bundle bundle = new Bundle();
            bundle.putString("habitoId", habito.getId());
            Navigation.findNavController(requireView()).navigate(R.id.action_nav_inicio_to_nav_detalle_habito, bundle);
        });
        binding.rvHabitosHoy.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvHabitosHoy.setAdapter(habitosAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        binding = null;
    }
}
