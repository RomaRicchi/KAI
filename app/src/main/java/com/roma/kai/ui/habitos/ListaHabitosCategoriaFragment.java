package com.roma.kai.ui.habitos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.roma.kai.R;
import com.roma.kai.databinding.FragmentListaHabitosCategoriaBinding;
import com.roma.kai.model.entity.Habito;
import java.util.ArrayList;
import java.util.List;

public class ListaHabitosCategoriaFragment extends Fragment {

    private FragmentListaHabitosCategoriaBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListaHabitosCategoriaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String categoria = "Categoría";
        if (getArguments() != null) {
            categoria = getArguments().getString("categoriaNombre", "Categoría");
        }
        
        // Mostrar el nombre de la categoría debajo del logo
        binding.txtCategoriaNombreHeader.setText(categoria);

        binding.btnBackLista.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });

        setupRecyclerView(categoria);
    }

    private void setupRecyclerView(String categoria) {
        // Lista de hábitos simulada filtrada por la categoría seleccionada
        List<Habito> habitosFiltrados = new ArrayList<>();
        habitosFiltrados.add(new Habito("Beber 2L de agua", categoria, 10, 0, false, 0));
        habitosFiltrados.add(new Habito("Caminar 30 min", categoria, 15, 0, false, 0));
        habitosFiltrados.add(new Habito("Meditación matutina", categoria, 20, 0, false, 0));

        // Usamos el adaptador con CheckBox
        HabitosSeleccionAdapter adapter = new HabitosSeleccionAdapter(habitosFiltrados, (habito, isChecked) -> {
            if (isChecked) {
                // Al marcar el check, simulamos que se agrega y volvemos a Mis Hábitos
                Navigation.findNavController(requireView()).navigate(R.id.action_nav_lista_habitos_categoria_to_nav_habitos);
            }
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
