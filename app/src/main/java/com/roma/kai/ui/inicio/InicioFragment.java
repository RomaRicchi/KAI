package com.roma.kai.ui.inicio;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable kaiAnimationRunnable = new Runnable() {
        @Override
        public void run() {
            playKaiAnimation();
            handler.postDelayed(this, 10000); // Repetir cada 10 segundos
        }
    };

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
        startKaiAnimationLoop();
    }

    private void startKaiAnimationLoop() {
        handler.postDelayed(kaiAnimationRunnable, 5000); // Empezar tras 5 segundos
    }

    private void playKaiAnimation() {
        if (binding == null || inicioVM.getInicioUiState().getValue() == null) return;

        // No animar si todavía está cargando o si no hubo éxito
        if (inicioVM.getInicioUiState().getValue().isLoading() || !inicioVM.getInicioUiState().getValue().isSuccess()) {
            return;
        }

        int eyesOpenMouthClosed = R.drawable.kai1;
        int eyesClosedMouthClosed = R.drawable.cerrado_frente;
        int eyesOpenMouthOpen = R.drawable.boca_frente;

        long blinkDuration = 200;
        long talkDuration = 150;

        // --- INICIO SECUENCIA ---
        
        // 1. Primer parpadeo
        binding.imgKaiHome.setImageResource(eyesClosedMouthClosed);

        binding.imgKaiHome.postDelayed(() -> {
            if (binding == null) return;
            binding.imgKaiHome.setImageResource(eyesOpenMouthClosed);
        }, blinkDuration);

        // 2. Hablar (Abre/Cierra 1)
        long startTalk1 = blinkDuration + 300;
        binding.imgKaiHome.postDelayed(() -> {
            if (binding == null) return;
            binding.imgKaiHome.setImageResource(eyesOpenMouthOpen);
        }, startTalk1);

        binding.imgKaiHome.postDelayed(() -> {
            if (binding == null) return;
            binding.imgKaiHome.setImageResource(eyesOpenMouthClosed);
        }, startTalk1 + talkDuration);

        // 3. Hablar (Abre/Cierra 2)
        long startTalk2 = startTalk1 + (talkDuration * 2);
        binding.imgKaiHome.postDelayed(() -> {
            if (binding == null) return;
            binding.imgKaiHome.setImageResource(eyesOpenMouthOpen);
        }, startTalk2);

        binding.imgKaiHome.postDelayed(() -> {
            if (binding == null) return;
            binding.imgKaiHome.setImageResource(eyesOpenMouthClosed);
        }, startTalk2 + talkDuration);

        // 4. Segundo parpadeo
        long startBlink2 = startTalk2 + (talkDuration * 2) + 200;
        binding.imgKaiHome.postDelayed(() -> {
            if (binding == null) return;
            binding.imgKaiHome.setImageResource(eyesClosedMouthClosed);
        }, startBlink2);

        // 5. Volver al estado original del servidor
        binding.imgKaiHome.postDelayed(() -> {
            if (binding != null && inicioVM.getInicioUiState().getValue() != null) {
                String key = inicioVM.getInicioUiState().getValue().getKaiImageKey();
                if (key != null) {
                    binding.imgKaiHome.setImageResource(ImageUi.getDrawable(key));
                }
            }
        }, startBlink2 + blinkDuration);
    }

    private void setupObservers() {
        inicioVM.getInicioUiState().observe(getViewLifecycleOwner(), state -> {
            if (state == null) return;

            // Loading state
            binding.progressBarHome.setVisibility(state.isLoading() ? View.VISIBLE : View.GONE);
            binding.layoutHomeContent.setVisibility(state.isLoading() ? View.INVISIBLE : View.VISIBLE);

            if (state.isSuccess()) {
                habitosAdapter.submitList(state.getHabitosDiarios());
                binding.tvHomeXp.setText(state.getFormattedXp());
                binding.tvHomeRacha.setText(state.getFormattedRacha());
                
                // Mensaje motivacional
                if (state.getMensajeMotivacional() != null && !state.getMensajeMotivacional().isEmpty()) {
                    binding.cardMessage.setVisibility(View.VISIBLE);
                    binding.tvMotivationalMessage.setText(state.getMensajeMotivacional());
                } else {
                    binding.cardMessage.setVisibility(View.GONE);
                }

                // Imagen de Kai
                if (state.getKaiImageKey() != null) {
                    if (state.getKaiImageKey().startsWith("http")) {
                        Glide.with(this).load(state.getKaiImageKey()).into(binding.imgKaiHome);
                    } else {
                        Glide.with(this).load(ImageUi.getDrawable(state.getKaiImageKey())).into(binding.imgKaiHome);
                    }
                }
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
        handler.removeCallbacks(kaiAnimationRunnable); // Detener el bucle para evitar fugas de memoria
        binding = null;
    }
}
