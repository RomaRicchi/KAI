package com.roma.kai.ui.perfil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.roma.kai.R;
import com.roma.kai.data.remote.RetrofitClient;
import com.roma.kai.databinding.DialogEditProfileBinding;
import com.roma.kai.databinding.FragmentPerfilBinding;
import com.roma.kai.model.dto.ProfileResponse;
import com.roma.kai.model.dto.ProfileSummary;
import com.roma.kai.model.entity.UsuarioEntity;
import com.roma.kai.session.SessionManager;
import com.roma.kai.utils.OnSafeClickListener;
import com.roma.kai.utils.UiMessage;
import com.roma.kai.utils.UiMessageHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Locale;

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
                    if (selectedImageUri != null) uploadImage(selectedImageUri);
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
        perfilVM.loadPerfil();
    }

    private void setupListeners() {
        binding.cardAvatar.setOnClickListener(new OnSafeClickListener() {
            @Override
            public void onSafeClick(View v) {
                openImagePicker();
            }
        });
        binding.btnEditProfile.setOnClickListener(new OnSafeClickListener() {
            @Override
            public void onSafeClick(View v) {
                showEditProfileDialog();
            }
        });
        binding.rowConfiguration.setOnClickListener(new OnSafeClickListener() {
            @Override
            public void onSafeClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_perfil_to_nav_configuration);
            }
        });
        binding.rowLogout.setOnClickListener(new OnSafeClickListener() {
            @Override
            public void onSafeClick(View v) {
                showLogoutConfirmation();
            }
        });
    }

    private void setupObservers() {
        perfilVM.getPerfilUiState().observe(getViewLifecycleOwner(), state -> {
            if (state == null) return;
            boolean busy = state.isLoading() || state.isSaving();
            binding.progressBarPerfil.setVisibility(busy ? View.VISIBLE : View.GONE);
            binding.btnEditProfile.setEnabled(!busy);
            binding.cardAvatar.setEnabled(!busy);

            if (state.getProfile() != null) {
                binding.layoutPerfilContent.setVisibility(View.VISIBLE);
                renderProfile(state.getProfile());
            } else {
                binding.layoutPerfilContent.setVisibility(View.INVISIBLE);
            }
        });

        perfilVM.getEventUiMessage().observe(getViewLifecycleOwner(), event -> {
            UiMessage message = event.obtenerContenidoSiNoManejado();
            if (message != null) {
                UiMessageHelper.showMessage(binding.getRoot(), requireContext(), message);
            }
        });
    }

    private void renderProfile(ProfileResponse profile) {
        UsuarioEntity user = profile.getUsuario();
        if (user != null) {
            binding.tvPerfilNombre.setText(user.getNombre());
            binding.tvPerfilEmail.setText(user.getEmail());
            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                binding.tvPerfilUsername.setVisibility(View.GONE);
            } else {
                binding.tvPerfilUsername.setVisibility(View.VISIBLE);
                binding.tvPerfilUsername.setText("@" + user.getUsername());
            }
            renderProfileImage(user.getFotoPerfil());
        }

        ProfileSummary summary = profile.getResumen();
        if (summary != null) {
            binding.tvPerfilXp.setText(NumberFormat.getIntegerInstance(Locale.getDefault())
                    .format(summary.getXpTotal()));
            binding.tvPerfilStreak.setText(getString(
                    R.string.formato_dias,
                    summary.getRachaGlobal()
            ));
            binding.tvPerfilInactive.setText(String.valueOf(summary.getDiasInactivo()));
        }
    }

    private void renderProfileImage(String profilePhoto) {
        if (profilePhoto == null || profilePhoto.trim().isEmpty()) {
            binding.imgPerfilAvatar.setImageResource(R.drawable.ic_user_vector);
            binding.imgPerfilAvatar.setPadding(22, 22, 22, 22);
            binding.imgPerfilAvatar.setImageTintList(
                    requireContext().getColorStateList(R.color.kai_primary)
            );
            return;
        }

        String imageUrl = profilePhoto.startsWith("http")
                ? profilePhoto
                : RetrofitClient.BASE_URL.replaceAll("/$", "") + profilePhoto;
        binding.imgPerfilAvatar.setPadding(0, 0, 0, 0);
        binding.imgPerfilAvatar.setImageTintList(null);
        Glide.with(this)
                .load(imageUrl)
                .circleCrop()
                .placeholder(R.drawable.ic_user_vector)
                .error(R.drawable.ic_user_vector)
                .into(binding.imgPerfilAvatar);
    }

    private void showEditProfileDialog() {
        PerfilUiState state = perfilVM.getPerfilUiState().getValue();
        if (state == null || state.getUsuario() == null) return;

        UsuarioEntity user = state.getUsuario();
        DialogEditProfileBinding dialogBinding = DialogEditProfileBinding.inflate(getLayoutInflater());
        dialogBinding.inputName.setText(user.getNombre());
        dialogBinding.inputUsername.setText(user.getUsername());

        androidx.appcompat.app.AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.profile_edit_title)
                .setView(dialogBinding.getRoot())
                .setNegativeButton(R.string.action_cancel, null)
                .setPositiveButton(R.string.action_save, null)
                .create();

        dialog.setOnShowListener(ignored -> dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(new OnSafeClickListener() {
                    @Override
                    public void onSafeClick(View v) {
                        String name = textOf(dialogBinding.inputName);
                        String username = textOf(dialogBinding.inputUsername);
                        dialogBinding.inputNameLayout.setError(null);
                        dialogBinding.inputUsernameLayout.setError(null);

                        if (name.length() < 2) {
                            dialogBinding.inputNameLayout.setError("Mínimo 2 caracteres");
                            return;
                        }
                        if (!username.isEmpty() && username.length() < 3) {
                            dialogBinding.inputUsernameLayout.setError("Mínimo 3 caracteres");
                            return;
                        }
                        if (username.isEmpty() && user.getUsername() != null &&
                                !user.getUsername().trim().isEmpty()) {
                            dialogBinding.inputUsernameLayout.setError("El username no puede quedar vacío");
                            return;
                        }

                        perfilVM.updateProfile(name, username);
                        dialog.dismiss();
                    }
                }));
        dialog.show();
    }

    private String textOf(android.widget.EditText input) {
        return input.getText() == null ? "" : input.getText().toString().trim();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        imagePickerLauncher.launch(Intent.createChooser(intent, "Selecciona una foto"));
    }

    private void showLogoutConfirmation() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.logout_confirmation_title)
                .setMessage(R.string.logout_confirmation_message)
                .setNegativeButton(R.string.action_cancel, null)
                .setPositiveButton(R.string.menu_logout, (dialog, which) ->
                        SessionManager.getInstance(requireContext()).logout())
                .show();
    }

    private void uploadImage(Uri uri) {
        try {
            File file = getFileFromUri(uri);
            if (file == null) return;
            RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/*"));
            MultipartBody.Part body = MultipartBody.Part.createFormData(
                    "foto",
                    file.getName(),
                    requestFile
            );
            perfilVM.uploadProfileImage(body);
        } catch (Exception exception) {
            UiMessageHelper.showMessage(
                    binding.getRoot(),
                    requireContext(),
                    new UiMessage("Error al procesar imagen", UiMessage.Type.ERROR)
            );
        }
    }

    private File getFileFromUri(Uri uri) throws Exception {
        try (InputStream inputStream = requireContext().getContentResolver().openInputStream(uri)) {
            if (inputStream == null) return null;
            File tempFile = File.createTempFile("profile_image", ".jpg", requireContext().getCacheDir());
            try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            return tempFile;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
