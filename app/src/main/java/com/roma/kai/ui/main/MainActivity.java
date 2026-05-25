package com.roma.kai.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.roma.kai.R;
import com.roma.kai.databinding.ActivityMainBinding;
import com.roma.kai.session.SessionManager;
import com.roma.kai.ui.login.LoginActivity;
import com.roma.kai.utils.UiMessage;
import com.roma.kai.utils.UiMessageHelper;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private SessionManager sessionManager;
    private ActivityMainBinding binding;
    private MainViewModel mainVM;
    private static final int PERMISO_UBICACION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mainVM = new ViewModelProvider(this).get(MainViewModel.class);
        sessionManager = SessionManager.getInstance(this);

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_main);
        NavController navController = navHostFragment.getNavController();

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_perfil, R.id.nav_habitos, R.id.nav_kai)
                .setOpenableLayout(drawer)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.nav_logout) {
                showLogoutConfirmation();
                return true;
            }

            boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
            if(handled) {
                binding.drawerLayout.closeDrawers();
            }
            return handled;
        });

        verificarPermisosUbicacion();
        setupObservers();
        setupIntent();

        mainVM.loadMe();
    }

    private void setupObservers() {
        sessionManager.getSessionExpired().observe(this, event -> {
            Boolean expired = event.obtenerContenidoSiNoManejado();
            if(expired == null)  return;
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        //USAR CUANDO SE NECESITE!!
//        mainVM.getMainUiState().observe(this, mainUiState -> {
//            desarrollar  cuando se necesite
//        });
//
        mainVM.getEventUiMessage().observe(this, eventUiMessage -> {
            UiMessage uiMessage = eventUiMessage.obtenerContenidoSiNoManejado();
            if(uiMessage == null) return;
            UiMessageHelper.showMessage(binding.getRoot(), MainActivity.this, uiMessage);
        });
    }

    public void setupIntent() {
        Intent intent = getIntent();
        if(intent == null) return;

        UiMessage uiMessage = (UiMessage) intent.getSerializableExtra("messageTo");
        if(uiMessage != null) {
            UiMessageHelper.showMessage(binding.getRoot(), MainActivity.this, uiMessage);
        }
    }

    private void showLogoutConfirmation() {
        Snackbar.make(binding.getRoot(), "¿Desea cerrar la sesión?", Snackbar.LENGTH_LONG)
                .setAction("Cerrar Sesión", v -> sessionManager.logout())
                .show();
    }

    private void verificarPermisosUbicacion() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, getString(R.string.permiso_ubicacion_necesario), Toast.LENGTH_LONG).show();
            }

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISO_UBICACION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISO_UBICACION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido
            } else {
                // Permiso denegado
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
