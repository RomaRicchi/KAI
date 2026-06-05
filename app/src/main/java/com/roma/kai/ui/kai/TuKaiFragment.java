package com.roma.kai.ui.kai;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.roma.kai.databinding.FragmentTuKaiBinding;
import com.roma.kai.utils.ImageUi;
import com.roma.kai.utils.UiMessage;
import com.roma.kai.utils.UiMessageHelper;

public class TuKaiFragment extends Fragment {

    private FragmentTuKaiBinding binding;
    private TuKaiViewModel tuKaiVM;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTuKaiBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tuKaiVM = new ViewModelProvider(this).get(TuKaiViewModel.class);

        setupObservers();
        tuKaiVM.loadTuKaiData();
    }

    private void setupObservers() {
        tuKaiVM.getTuKaiUiState().observe(getViewLifecycleOwner(), state -> {
            if (state == null) return;

            if (state.isSuccess()) {
                binding.txtKaiStage.setText(state.getStage());
                
                // Vigor/Energía
                binding.progressVigorCircle.setProgress(state.getEnergy());
                binding.progressVigorHorizontal.setProgress(state.getEnergy());
                binding.txtVigorPercent.setText(state.getEnergy() + "%");
                binding.txtVigorDesc.setText(state.getEnergyDesc());

                // Personalidad
                binding.txtPersonalityTitle.setText(state.getPersonalityTitle());
                binding.txtEmotionalMessage.setText(state.getEmotionalMessage());

                // Radar Chart
                if (state.getAttributeLabels() != null && state.getAttributeValues() != null) {
                    binding.radarChart.setData(state.getAttributeLabels(), state.getAttributeValues());
                }
                binding.txtPredominante.setText(state.getCategoryPredominante());
                binding.txtMenosAvanzada.setText(state.getCategoryMenosAvanzada());

                // Imagen de Kai
                if (state.getKaiImageKey() != null) {
                    if (state.getKaiImageKey().startsWith("http")) {
                        Glide.with(this).load(state.getKaiImageKey()).into(binding.imgKaiBig);
                    } else {
                        Glide.with(this).load(ImageUi.getDrawable(state.getKaiImageKey())).into(binding.imgKaiBig);
                    }
                }
            }
        });

        tuKaiVM.getEventUiMessage().observe(getViewLifecycleOwner(), event -> {
            UiMessage message = event.obtenerContenidoSiNoManejado();
            if (message != null) {
                UiMessageHelper.showMessage(binding.getRoot(), requireContext(), message);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
