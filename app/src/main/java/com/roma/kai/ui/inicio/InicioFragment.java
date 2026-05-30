package com.roma.kai.ui.inicio;

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

import com.bumptech.glide.Glide;
import com.roma.kai.R;
import com.roma.kai.databinding.FragmentInicioBinding;
import com.roma.kai.utils.ImageUi;
import com.roma.kai.utils.UiMessage;
import com.roma.kai.utils.UiMessageHelper;


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

        inicioVM.loadHomeView();
    }

    private void setupObservers() {
        inicioVM.getInicioUiState().observe(getViewLifecycleOwner(), inicioUiState -> {
            if(inicioUiState == null) return;

            // Gestión de carga
            if (inicioUiState.isLoading()) {
                binding.progressBarHome.setVisibility(View.VISIBLE);
                binding.layoutHomeContent.setVisibility(View.INVISIBLE);
            } else {
                binding.progressBarHome.setVisibility(View.GONE);
                binding.layoutHomeContent.setVisibility(View.VISIBLE);
            }

            if(inicioUiState.isSuccess()) {
                habitosAdapter.submitList(inicioUiState.getHabitosDiarios());
                binding.tvHomeXp.setText(inicioUiState.getXpTotal() + " XP");
                binding.tvHomeRacha.setText(inicioUiState.getRachaActual() + " Días");
                
                // Mensaje motivacional
                if (inicioUiState.getMensajeMotivacional() != null && !inicioUiState.getMensajeMotivacional().isEmpty()) {
                    binding.cardMessage.setVisibility(View.VISIBLE);
                    binding.tvMotivationalMessage.setText(inicioUiState.getMensajeMotivacional());
                } else {
                    binding.cardMessage.setVisibility(View.GONE);
                }

                // Imagen de Kai con Resolver ImageUi
                if (inicioUiState.getEstadoKai() != null) {
                    String imgKai = inicioUiState.getEstadoKai().getImageKai();
                    
                    if (imgKai != null && imgKai.startsWith("http")) {
                        Glide.with(this).load(imgKai).into(binding.imgKaiHome);
                    } else {
                        // Usamos el resolver para el estado emocional de Kai
                        String key = (imgKai != null) ? imgKai : inicioUiState.getEstadoKai().getEstadoActual();
                        Glide.with(this).load(ImageUi.getDrawable(key)).into(binding.imgKaiHome);
                    }
                }
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
        habitosAdapter = new InicioHabitosAdapter(habito -> {
            Bundle bundle = new Bundle();

            bundle.putString(
                    "habitoId",
                    habito.getId()
            );

            Navigation.findNavController(requireView()).navigate(
                    R.id.action_nav_inicio_to_nav_detalle_habito,
                    bundle
            );
        });
        binding.rvHabitosHoy.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvHabitosHoy.setAdapter(habitosAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
