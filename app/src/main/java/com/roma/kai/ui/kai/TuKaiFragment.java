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
import com.roma.kai.model.dto.KaiAttributeDto;
import com.roma.kai.utils.AppMapper;
import com.roma.kai.utils.ImageUi;
import com.roma.kai.utils.UiMessage;
import com.roma.kai.utils.UiMessageHelper;

import java.util.ArrayList;
import java.util.List;

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

                binding.txtKaiStage.setText(state.getEstadoKai().getEtapaActual());
                
                // Energía
                binding.progressVigorCircle.setProgress(state.getEstadoKai().getEnergia());
                binding.progressVigorHorizontal.setProgress(state.getEstadoKai().getEnergia());
                binding.txtVigorPercent.setText(state.getEstadoKai().getEnergia() + "%");
                binding.txtVigorDesc.setText(state.getEstadoKai().getEnergia() + "/100 - Enérgico (Mejora con hábitos)");

                // Personalidad
                binding.txtPersonalityTitle.setText("Personalidad Principal: " + state.getCategoriaDominante().getNombre());
                binding.txtEmotionalMessage.setText(state.getMensajeEmocional());
                
                // Imagen de personalidad dinámica
                if (state.getCategoriaDominante() != null) {
                    int iconResId = ImageUi.getDrawable(state.getCategoriaDominante().getNombre());
                    if (iconResId != 0) {
                        binding.imgPersonalityIcon.setImageResource(iconResId);
                        // Aseguramos que no tenga tinte que lo tape
                        binding.imgPersonalityIcon.setImageTintList(null);
                    }
                }

                List<String> labels = new ArrayList<>();
                List<Float> values = new ArrayList<>();

                for (KaiAttributeDto attr : state.getAtributos()) {
                    labels.add(AppMapper.getCategoryByAttribute(attr.getAtributo()));
                    values.add((float) attr.getValor());
                }

                // Radar Chart
                if (labels != null && values != null) {
                    binding.radarChart.setData(labels, values);
                }
                binding.txtMenosAvanzada.setText("Recuerda trabajar en tu " + state.getCategoriaMenosDominante().getNombre());

                // Imagen de Kai
                if (state.getEstadoKai() != null) {
                    binding.imgKaiBig.setVisibility(View.VISIBLE);
                    if (state.getEstadoKai().getImageKai() != null && state.getEstadoKai().getImageKai().startsWith("http")) {
                        Glide.with(this).load(state.getEstadoKai().getImageKai()).into(binding.imgKaiBig);
                    } else {
                        Glide.with(this).load(ImageUi.getDrawable(state.getEstadoKai().getEstadoActual())).into(binding.imgKaiBig);
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
