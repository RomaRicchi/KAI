package com.roma.kai.ui.habitos.selectCategoria.addHabito;

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
import com.roma.kai.databinding.FragmentListaHabitosCategoriaBinding;
import com.roma.kai.model.dto.HabitoCatalogoDto;
import com.roma.kai.utils.UiMessage;
import com.roma.kai.utils.UiMessageHelper;

import java.util.ArrayList;
import java.util.List;

public class ListaHabitosCategoriaFragment extends Fragment {

    private FragmentListaHabitosCategoriaBinding binding;
    private ListaHabitosCategoriaViewModel listaHabitosVM;
    private HabitosSeleccionAdapter adapter;
    private List<HabitoCatalogoDto> habitosList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListaHabitosCategoriaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaHabitosVM = new ViewModelProvider(this).get(ListaHabitosCategoriaViewModel.class);

        String categoryId = null;
        String categoriaNombre = "Categoría";
        if (getArguments() != null) {
            categoryId = getArguments().getString("categoryId", null);
            categoriaNombre = getArguments().getString("categoriaNombre", "Categoría");
        }
        
        binding.txtCategoriaNombreHeader.setText(categoriaNombre);

        setupRecyclerView();
        setupObservers();

        if (categoryId != null) {
            listaHabitosVM.loadHabitos(categoryId);
        }
    }

    private void setupObservers() {
        listaHabitosVM.getUiState().observe(getViewLifecycleOwner(), uiState -> {
            binding.progressBarHabitosCatalogo.setVisibility(uiState.isLoading() ? View.VISIBLE : View.GONE);
            
            if (uiState.isSuccess()) {
                habitosList.clear();
                habitosList.addAll(uiState.getHabitos());
                adapter.notifyDataSetChanged();
            }

            if (uiState.isSelectSuccess()) {
                Navigation.findNavController(requireView()).navigate(R.id.action_nav_lista_habitos_categoria_to_nav_habitos);
            }
        });

        listaHabitosVM.getEventUiMessage().observe(getViewLifecycleOwner(), event -> {
            if(event == null) return;

            UiMessage message =
                    event.obtenerContenidoSiNoManejado();

            if(message != null){
                UiMessageHelper.showMessage(binding.getRoot(), requireContext(), message);
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new HabitosSeleccionAdapter(habitosList, habito -> {
            listaHabitosVM.seleccionarHabito(habito.getId());
        });

        binding.rvHabitosCategoria.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvHabitosCategoria.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
