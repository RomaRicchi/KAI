package com.roma.kai.session;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.roma.kai.model.entity.ConfiguracionUsuarioEntity;
import com.roma.kai.model.entity.UsuarioEntity;
import com.roma.kai.utils.Event;

public class SessionManager {
    private static SessionManager instance;
    private final MutableLiveData<Event<Boolean>> sessionExpired = new MutableLiveData<>();
    private final SharedPreferences prefs;
    private final Gson gson = new Gson();
    private UsuarioEntity currentUser;
    private ConfiguracionUsuarioEntity currentConfig;

    private SessionManager(Context context) {
        prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);

        loadSession();
    }

    public static synchronized SessionManager getInstance(Context context) {
        if(instance == null) {
            instance = new SessionManager(context.getApplicationContext());
        }

        return instance;
    }

    public void saveToken(String token) {
        prefs.edit().putString("token", token).apply();
    }

    public String getToken() {
        return prefs.getString("token", null);
    }

    public void saveUser(UsuarioEntity user) {
        currentUser = user;

        prefs.edit() .putString("user", gson.toJson(user)).apply();
    }

    public UsuarioEntity getUser() {
        return currentUser;
    }

    public void saveConfig(ConfiguracionUsuarioEntity config) {
        currentConfig = config;

        prefs.edit().putString("config", gson.toJson(config)).apply();
    }

    public ConfiguracionUsuarioEntity getConfig() {
        return currentConfig;
    }

    private void loadSession() {

        String userJson = prefs.getString("user", null);

        if(userJson != null) {
            currentUser = gson.fromJson(userJson, UsuarioEntity.class);
        }

        String configJson = prefs.getString("config", null);

        if(configJson != null) {
            currentConfig = gson.fromJson(configJson, ConfiguracionUsuarioEntity.class);
        }
    }

    public LiveData<Event<Boolean>> getSessionExpired() {
        return sessionExpired;
    }

    public void logout() {
        clearSession();
        sessionExpired.postValue(new Event<>(true));
    }

    public void clearSession() {
        currentUser = null;
        currentConfig = null;

        prefs.edit().clear().apply();
    }
}
