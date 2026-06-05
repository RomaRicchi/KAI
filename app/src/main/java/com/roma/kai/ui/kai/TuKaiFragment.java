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
                binding.cardKaiStage.setVisibility(View.VISIBLE);
                binding.cardVigor.setVisibility(View.VISIBLE);
                binding.cardPersonality.setVisibility(View.VISIBLE);
                binding.txtRadarTitle.setVisibility(View.VISIBLE);
                binding.radarChart.setVisibility(View.VISIBLE);
                binding.layoutRadarFooter.setVisibility(View.VISIBLE);

                binding.txtKaiStage.setText(state.getStage());
                
                // Energía
                binding.progressVigorCircle.setProgress(state.getEnergy());
                binding.progressVigorHorizontal.setProgress(state.getEnergy());
                binding.txtVigorPercent.setText(state.getEnergy() + "%");
                binding.txtVigorDesc.setText(state.getEnergyDesc());

                // Personalidad
                binding.txtPersonalityTitle.setText("Personalidad Principal: " + state.getCategoryPredominante());
                binding.txtEmotionalMessage.setText(state.getEmotionalMessage());
                
                // Imagen de personalidad dinámica
                if (state.getCategoryPredominanteKey() != null) {
                    int iconResId = ImageUi.getDrawable(state.getCategoryPredominanteKey());
                    if (iconResId != 0) {
                        binding.imgPersonalityIcon.setImageResource(iconResId);
                        // Aseguramos que no tenga tinte que lo tape
                        binding.imgPersonalityIcon.setImageTintList(null);
                    }
                }

                // Radar Chart
                if (state.getAttributeLabels() != null && state.getAttributeValues() != null) {
                    binding.radarChart.setData(state.getAttributeLabels(), state.getAttributeValues());
                }
                binding.txtMenosAvanzada.setText(state.getCategoryMenosAvanzada());

                // Imagen de Kai
                if (state.getKaiImageKey() != null) {
                    binding.imgKaiBig.setVisibility(View.VISIBLE);
                    if (state.getKaiImageKey().startsWith("http")) {
                        Glide.with(this).load(state.getKaiImageKey()).into(binding.imgKaiBig);
                    } else {
                        Glide.with(this).load(ImageUi.getDrawable(state.getKaiImageKey())).into(binding.imgKaiBig);
                    }
                }
            } else {
                binding.imgKaiBig.setVisibility(View.INVISIBLE);
                binding.cardKaiStage.setVisibility(View.GONE);
                binding.cardVigor.setVisibility(View.GONE);
                binding.cardPersonality.setVisibility(View.GONE);
                binding.txtRadarTitle.setVisibility(View.GONE);
                binding.radarChart.setVisibility(View.GONE);
                binding.layoutRadarFooter.setVisibility(View.GONE);
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
