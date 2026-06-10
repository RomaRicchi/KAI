package com.roma.kai.utils;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.R;

/**
 * Clase responsable de gestionar la lógica de animación y sonido de Kai.
 * Centraliza los tiempos, frames y eventos de "voz".
 */
public class AnimationKai {

    private final MutableLiveData<Integer> kaiImageResource = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> playSoundEvent = new MutableLiveData<>();
    
    // --- LIVEDATA PARA LA LUCIÉRNAGA ---
    private final MutableLiveData<Integer> fireflyImageResource = new MutableLiveData<>();
    private final MutableLiveData<Float> fireflyTranslationX = new MutableLiveData<>();
    private final MutableLiveData<Float> fireflyTranslationY = new MutableLiveData<>();
    private final MutableLiveData<Integer> fireflyVisibility = new MutableLiveData<>();
    
    private final Handler handler = new Handler(Looper.getMainLooper());
    private boolean isFirstAnimation = true;
    private String lastKaiKey = null;
    private boolean allowFirefly = false; // Flag para activar/desactivar Animación #2

    private final Runnable kaiAnimationRunnable = new Runnable() {
        @Override
        public void run() {
            // Animación #1 (Hablar/Parpadear) es solo para el Inicio (allowFirefly = false)
            // Para Tokai (allowFirefly = true) usamos solo la Animación #2 (Luciérnaga)
            if (allowFirefly) {
                executeFireflySequence();
                // El tiempo total de la secuencia es kaiFrames.length * frameTime (13 * 220 = 2860ms)
                // Usamos un delay idéntico para que el bucle sea infinito y sin pausas.
                handler.postDelayed(this, 2860);
            } else {
                executeSequence();
                handler.postDelayed(this, 10000);
            }
        }
    };

    public AnimationKai() {
        fireflyVisibility.setValue(View.GONE);
    }

    public LiveData<Integer> getKaiImageResource() { return kaiImageResource; }
    public LiveData<Event<Boolean>> getPlaySoundEvent() { return playSoundEvent; }
    public LiveData<Integer> getFireflyImageResource() { return fireflyImageResource; }
    public LiveData<Float> getFireflyTranslationX() { return fireflyTranslationX; }
    public LiveData<Float> getFireflyTranslationY() { return fireflyTranslationY; }
    public LiveData<Integer> getFireflyVisibility() { return fireflyVisibility; }

    /**
     * Inicia el bucle de animación.
     * @param initialKey La clave del estado actual de Kai.
     * @param allowFirefly Si debe incluir la Animación #2 de la luciérnaga.
     */
    public void startAnimation(String initialKey, boolean allowFirefly) {
        this.lastKaiKey = initialKey;
        this.allowFirefly = allowFirefly;
        
        // Forzamos Kai 1 (kai_base / anim1) al inicio para que predomine
        if (allowFirefly) {
            kaiImageResource.setValue(ImageUi.getDrawable("anim1"));
        } else {
            kaiImageResource.setValue(ImageUi.getDrawable("kai_base"));
        }
        
        fireflyVisibility.setValue(View.GONE);
        handler.removeCallbacks(kaiAnimationRunnable);
        // Reducimos el delay inicial de 5s a 500ms para que comience rápido
        handler.postDelayed(kaiAnimationRunnable, 500);
    }

    // Sobrecarga para mantener compatibilidad si no se especifica el flag
    public void startAnimation(String initialKey) {
        startAnimation(initialKey, false);
    }

    public void stopAnimation() {
        handler.removeCallbacks(kaiAnimationRunnable);
    }

    public void updateBaseImage(String key) {
        this.lastKaiKey = key;
        if (key != null) {
            kaiImageResource.setValue(ImageUi.getDrawable(key));
        }
    }

    private void executeSequence() {
        int baseFrame = ImageUi.getDrawable("kai_base");
        int eyesClosedFrame = ImageUi.getDrawable("kai_ojos_cerrados");
        int mouthOpenFrame = ImageUi.getDrawable("kai_boca_abierta");
        long blinkDuration = 200;
        long talkDuration = 250;

        // Empezamos asegurando que esté en baseFrame (Kai 1)
        kaiImageResource.setValue(baseFrame);
        
        // El primer parpadeo ocurre tras un pequeño delay
        handler.postDelayed(() -> kaiImageResource.setValue(eyesClosedFrame), 500);
        handler.postDelayed(() -> kaiImageResource.setValue(baseFrame), 500 + blinkDuration);

        long startTalk1 = 500 + blinkDuration + 400;
        if (isFirstAnimation) {
            handler.postDelayed(() -> playSoundEvent.setValue(new Event<>(true)), startTalk1 - 50);
            isFirstAnimation = false;
        }

        for (int i = 0; i < 3; i++) {
            long offset = startTalk1 + (i * talkDuration * 2);
            handler.postDelayed(() -> kaiImageResource.setValue(mouthOpenFrame), offset);
            handler.postDelayed(() -> kaiImageResource.setValue(baseFrame), offset + talkDuration);
        }

        long startBlink2 = startTalk1 + (3 * talkDuration * 2) + 200;
        handler.postDelayed(() -> kaiImageResource.setValue(eyesClosedFrame), startBlink2);

        handler.postDelayed(() -> {
            if (lastKaiKey != null) {
                kaiImageResource.setValue(ImageUi.getDrawable(lastKaiKey));
            } else {
                kaiImageResource.setValue(baseFrame);
            }
        }, startBlink2 + blinkDuration);
    }

    private void executeFireflySequence() {
        final long frameTime = 220; // Más lento (de 120ms a 220ms)
        final String[] kaiFrames = {"anim1", "anim2", "anim3", "anim4", "anim5", "anim6", "anim7", "anim6", "anim5", "anim4", "anim3", "anim2", "anim1"};
        
        // Trayectoria suavizada para que no se detenga bruscamente
        final float[][] trajectory = {
            {0, -100}, {20, -115}, {40, -130}, {60, -115}, {80, -100}, {60, -85}, {40, -70}, 
            {20, -85}, {0, -100}, {-20, -115}, {-40, -130}, {-20, -115}, {0, -100}
        };
        
        fireflyVisibility.setValue(View.VISIBLE);

        for (int i = 0; i < kaiFrames.length; i++) {
            final int index = i;
            handler.postDelayed(() -> {
                kaiImageResource.setValue(ImageUi.getDrawable(kaiFrames[index]));
                fireflyImageResource.setValue(ImageUi.getDrawable(index % 2 == 0 ? "firefly_a" : "firefly_b"));
                fireflyTranslationX.setValue(trajectory[index][0]);
                fireflyTranslationY.setValue(trajectory[index][1]);

                if (index == kaiFrames.length - 1) {
                    // En Tokai, no ocultamos nada, el siguiente Runnable ya viene en camino
                    if (!allowFirefly) {
                        handler.postDelayed(() -> {
                            fireflyVisibility.setValue(View.GONE);
                            if (lastKaiKey != null) {
                                kaiImageResource.setValue(ImageUi.getDrawable(lastKaiKey));
                            } else {
                                kaiImageResource.setValue(ImageUi.getDrawable("kai_base"));
                            }
                        }, frameTime);
                    }
                }
            }, i * frameTime);
        }
    }
}
