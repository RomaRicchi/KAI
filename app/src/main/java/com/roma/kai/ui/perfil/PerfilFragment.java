package com.roma.kai.ui.perfil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.roma.kai.R;
import com.roma.kai.databinding.FragmentPerfilBinding;
import com.roma.kai.utils.UiMessage;
import com.roma.kai.utils.UiMessageHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private PerfilViewModel perfilVM;
    
    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        uploadImage(selectedImageUri);
                    }
                }
            }
    );

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
        setupListeners();
    }

    private void setupListeners() {
        binding.btnLogout.setOnClickListener(v -> perfilVM.logout());
        
        binding.cardAvatar.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            imagePickerLauncher.launch(Intent.createChooser(intent, "Selecciona una foto"));
        });

        // Opcional: Click largo para borrar
        binding.cardAvatar.setOnLongClickListener(v -> {
            perfilVM.deleteProfileImage();
            return true;
        });
    }

    private void setupObservers() {
        perfilVM.getPerfilUiState().observe(getViewLifecycleOwner(), perfilUiState -> {
            if(perfilUiState.isSuccess() && perfilUiState.getUsuario() != null) {
                binding.tvPerfilNombre.setText(perfilUiState.getUsuario().getNombre());
                binding.tvPerfilEmail.setText(perfilUiState.getUsuario().getEmail());
                
                // Carga de imagen con Glide según especificación del backend
                String fotoPerfil = perfilUiState.getUsuario().getFotoPerfil(); 

                if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
                    String baseUrl = com.roma.kai.data.remote.RetrofitClient.BASE_URL;
                    if (baseUrl.endsWith("/")) {
                        baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
                    }
                    String imageUrl = baseUrl + fotoPerfil;

                    Glide.with(this)
                            .load(imageUrl)
                            .circleCrop()
                            .placeholder(R.drawable.ic_camera_black_24dp)
                            .error(R.drawable.ic_camera_black_24dp)
                            .into(binding.imgPerfilAvatar);

                    binding.imgPerfilAvatar.setPadding(0, 0, 0, 0);
                    binding.imgPerfilAvatar.setImageTintList(null);
                } else {
                    binding.imgPerfilAvatar.setImageResource(R.drawable.ic_camera_black_24dp);
                    binding.imgPerfilAvatar.setPadding(20, 20, 20, 20);
                }
            }
        });

        perfilVM.getEventUiMessage().observe(getViewLifecycleOwner(), eventUiMessage -> {
            UiMessage uiMessage = eventUiMessage.obtenerContenidoSiNoManejado();
            if(uiMessage == null) return;
            UiMessageHelper.showMessage(binding.getRoot(), requireContext(), uiMessage);
        });
    }

    private void uploadImage(Uri uri) {
        try {
            File file = getFileFromUri(uri);
            if (file != null) {
                // Crear el RequestBody según la especificación
                RequestBody requestFile = RequestBody.create(
                        file,
                        MediaType.parse("image/*")
                );

                // Crear el MultipartBody.Part con la clave "foto"
                MultipartBody.Part body = MultipartBody.Part.createFormData(
                        "foto",
                        file.getName(),
                        requestFile
                );

                perfilVM.uploadProfileImage(body);
            }
        } catch (Exception e) {
            UiMessageHelper.showMessage(binding.getRoot(), requireContext(), new UiMessage("Error al procesar imagen", UiMessage.Type.ERROR));
        }
    }

    private File getFileFromUri(Uri uri) throws Exception {
        InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
        if (inputStream == null) return null;
        
        File tempFile = File.createTempFile("profile_image", ".jpg", requireContext().getCacheDir());
        FileOutputStream outputStream = new FileOutputStream(tempFile);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
        inputStream.close();
        return tempFile;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
