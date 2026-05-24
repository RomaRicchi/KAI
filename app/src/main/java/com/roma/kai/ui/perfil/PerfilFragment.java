package com.roma.kai.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.roma.kai.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
//    private PerfilViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        viewModel = new ViewModelProvider(this).get(PerfilViewModel.class);

//        observarPerfil();
//        viewModel.cargarPerfil();
    }

//    private void observarPerfil() {
//        viewModel.getEstadoPerfil().observe(getViewLifecycleOwner(), estado -> {
//            if (estado instanceof EstadoUI.EstadoExito) {
//                Usuario user = (Usuario) ((EstadoUI.EstadoExito<?>) estado).getDatos();
//                binding.tvPerfilNombre.setText(getString(R.string.perfil_nombre, user.getNombre()));
//                binding.tvPerfilEmail.setText(getString(R.string.perfil_email, user.getEmail()));
//                binding.tvPerfilNivel.setText(getString(R.string.perfil_nivel, user.getNivel()));
//            }
//        });
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
