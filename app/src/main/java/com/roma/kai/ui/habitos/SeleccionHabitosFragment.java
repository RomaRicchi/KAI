package com.roma.kai.ui.habitos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import com.roma.kai.R;
import com.roma.kai.databinding.FragmentSeleccionHabitosBinding;
import com.roma.kai.model.entity.Categoria;
import java.util.ArrayList;
import java.util.List;

public class SeleccionHabitosFragment extends Fragment {

    private FragmentSeleccionHabitosBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSeleccionHabitosBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        binding.btnBackSeleccion.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria("Salud", R.drawable.ic_launcher_foreground));
        categorias.add(new Categoria("Mente", R.drawable.ic_gallery_black_24dp));
        categorias.add(new Categoria("Productividad", R.drawable.ic_settings_black_24dp));
        categorias.add(new Categoria("Social", R.drawable.ic_launcher_foreground));
        categorias.add(new Categoria("Creatividad", R.drawable.ic_gallery_black_24dp));
        categorias.add(new Categoria("Bienestar", R.drawable.ic_settings_black_24dp));

        CategoriaAdapter adapter = new CategoriaAdapter(categorias, categoria -> {
            Bundle bundle = new Bundle();
            bundle.putString("categoriaNombre", categoria.getNombre());
            Navigation.findNavController(requireView()).navigate(R.id.action_nav_seleccion_habitos_to_nav_lista_habitos_categoria, bundle);
        });

        binding.rvCategorias.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.rvCategorias.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
