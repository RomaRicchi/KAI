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
import com.roma.kai.databinding.FragmentHabitosBinding;
import com.roma.kai.model.entity.Habito;
import java.util.ArrayList;
import java.util.List;

public class HabitosFragment extends Fragment {

    private FragmentHabitosBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHabitosBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fabAddHabito.setOnClickListener(v -> 
            Navigation.findNavController(v).navigate(R.id.action_nav_habitos_to_nav_seleccion_habitos)
        );

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        // Datos de prueba
        List<Habito> listaPrueba = new ArrayList<>();
        listaPrueba.add(new Habito("Tomar Agua", "Vitalidad", 10, 6, true, R.drawable.ic_gallery_black_24dp));
        listaPrueba.add(new Habito("Leer", "Mente", 20, 5, false, R.drawable.ic_gallery_black_24dp));
        listaPrueba.add(new Habito("Caminar", "Salud", 15, 3, false, R.drawable.ic_gallery_black_24dp));

        HabitosAdapter adapter = new HabitosAdapter(listaPrueba, habito -> {
            // Al hacer clic en un hábito, navegamos al detalle
            Navigation.findNavController(requireView()).navigate(R.id.action_nav_habitos_to_nav_detalle_habito);
        });

        binding.rvMisHabitos.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvMisHabitos.setAdapter(adapter);
        
        if (listaPrueba.isEmpty()) {
            binding.layoutEmptyHabitos.setVisibility(View.VISIBLE);
            binding.rvMisHabitos.setVisibility(View.GONE);
        } else {
            binding.layoutEmptyHabitos.setVisibility(View.GONE);
            binding.rvMisHabitos.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
