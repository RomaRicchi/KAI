package com.roma.kai.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.roma.kai.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private PerfilViewModel perfilVM;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        perfilVM = new ViewModelProvider(this).get(PerfilViewModel.class);

        setupObservers();
    }

    private void setupObservers() {
        perfilVM.getPerfilUiState().observe(getViewLifecycleOwner(), state -> {
            if(state.isSuccess() && state.getUsuario() != null) {
                binding.tvPerfilNombre.setText(state.getUsuario().getNombre());
                binding.tvPerfilEmail.setText(state.getUsuario().getEmail());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
