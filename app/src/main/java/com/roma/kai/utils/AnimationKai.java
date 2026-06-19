package com.roma.kai.utils;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.R;

/**
 * Gestor Central de Animaciones de Kai.
 * Diseñado para ser escalable, permitiendo múltiples tipos de animaciones cíclicas e independientes.
 */
public class AnimationKai {

    public enum AnimationType {
        HOME,
        TUKAI,
        CELEBRATION,
        RECOVERY,
        SLEEP,
        REFLECTION,
        EVOLUTION
    }

    // --- LIVEDATA ---
    private final MutableLiveData<Integer> kaiImageResource = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> playSoundEvent = new MutableLiveData<>();
    private final MutableLiveData<Integer> fireflyImageResource = new MutableLiveData<>();
    private final MutableLiveData<Float> fireflyTranslationX = new MutableLiveData<>();
    private final MutableLiveData<Float> fireflyTranslationY = new MutableLiveData<>();
    private final MutableLiveData<Integer> fireflyVisibility = new MutableLiveData<>();

    // --- CONTROL ---
    private final Handler handler = new Handler(Looper.getMainLooper());
    private AnimationType currentAnimation = AnimationType.HOME;
    private String lastKaiKey = null;
    private boolean isFirstRun = true;

    // --- BUCLE PRINCIPAL (DISPATCHER) ---
    private final Runnable mainAnimationLoop = new Runnable() {
        @Override
        public void run() {
            long cycleDuration;

            // Despacho de la animación activa
            switch (currentAnimation) {
                case TUKAI:
                    cycleDuration = executeTuKaiAnimation();
                    break;
                case CELEBRATION:
                    cycleDuration = executeCelebrationAnimation();
                    break;
                case SLEEP:
                    cycleDuration = executeSleepAnimation();
                    break;
                case HOME:
                default:
                    cycleDuration = executeHomeAnimation();
                    break;
            }

            // Reprogramar el siguiente ciclo tras finalizar la animación actual
            handler.postDelayed(this, cycleDuration);
        }
    };

    public AnimationKai() {
        fireflyVisibility.setValue(View.GONE);
    }

    // --- GETTERS ---
    public LiveData<Integer> getKaiImageResource() { return kaiImageResource; }
    public LiveData<Event<Boolean>> getPlaySoundEvent() { return playSoundEvent; }
    public LiveData<Integer> getFireflyImageResource() { return fireflyImageResource; }
    public LiveData<Float> getFireflyTranslationX() { return fireflyTranslationX; }
    public LiveData<Float> getFireflyTranslationY() { return fireflyTranslationY; }
    public LiveData<Integer> getFireflyVisibility() { return fireflyVisibility; }

    // --- API PÚBLICA ---

    /**
     * Inicia una animación específica.
     * @param initialKey Clave de imagen base (opcional).
     * @param type Tipo de animación a ejecutar.
     */
    public void startAnimation(String initialKey, AnimationType type) {
        stopAnimation(); // Limpieza absoluta de callbacks anteriores
        this.lastKaiKey = initialKey;
        this.currentAnimation = type;

        // Estado visual inicial inmediato
        setupInitialFrame(type);

        // Disparo del loop
        handler.postDelayed(mainAnimationLoop, 500);
    }

    public void startAnimation(AnimationType type) {
        startAnimation(null, type);
    }

    /**
     * @deprecated Usar startAnimation(String, AnimationType)
     */
    @Deprecated
    public void startAnimation(String initialKey, boolean isTuKai) {
        startAnimation(initialKey, isTuKai ? AnimationType.TUKAI : AnimationType.HOME);
    }

    public void stopAnimation() {
        handler.removeCallbacksAndMessages(null);
        fireflyVisibility.setValue(View.GONE);
    }

    public void updateBaseImage(String key) {
        this.lastKaiKey = key;
        // Solo actualizamos si estamos en HOME (donde los estados afectan a la base)
        if (currentAnimation == AnimationType.HOME && key != null) {
            kaiImageResource.setValue(ImageUi.getDrawable(key));
        }
    }

    private void setupInitialFrame(AnimationType type) {
        if (type == AnimationType.TUKAI) {
            kaiImageResource.setValue(ImageUi.getDrawable("anim1"));
        } else {
            kaiImageResource.setValue(ImageUi.getDrawable("kai_base"));
        }
    }

    // =========================================================================
    // IMPLEMENTACIÓN DE ANIMACIONES (AISLADAS)
    // =========================================================================

    /**
     * Animación HOME: Comportamiento de mascota virtual.
     * Secuencia: Quieto -> Parpadeo -> Abre boca/Miau -> Cierra boca -> Quieto.
     */
    private long executeHomeAnimation() {
        final int baseFrame = ImageUi.getDrawable("kai_base");
        final int eyesClosedFrame = ImageUi.getDrawable("kai_ojos_cerrados");
        final int mouthOpenFrame = ImageUi.getDrawable("kai_boca_abierta");
        
        final long blinkDur = 100; // Parpadeo más rápido
        final long meowDur = 800; 
        final long startDelay = 100; // Inicio casi inmediato

        // 1. Pose Inicial
        kaiImageResource.setValue(baseFrame);
        
        // 2. Parpadeo rápido
        handler.postDelayed(() -> kaiImageResource.setValue(eyesClosedFrame), startDelay);
        handler.postDelayed(() -> kaiImageResource.setValue(baseFrame), startDelay + blinkDur);

        // 3. Apertura de boca y Maullido
        // El sonido se dispara un poco antes de la imagen para compensar la latencia del hardware
        long startAction = startDelay + blinkDur + 50; 
        
        handler.postDelayed(() -> {
            // Disparar el audio un instante antes de cambiar el frame
            playSoundEvent.setValue(new Event<>(true));
            handler.postDelayed(() -> kaiImageResource.setValue(mouthOpenFrame), 30);
        }, startAction);

        // 4. Cierre de boca y vuelta al estado normal con protección anti-imagen rota
        long endAction = startAction + meowDur;
        handler.postDelayed(() -> {
            int finalRes = baseFrame;
            if (lastKaiKey != null && !lastKaiKey.isEmpty()) {
                int resolvedRes = ImageUi.getDrawable(lastKaiKey);
                if (resolvedRes != R.drawable.ic_gallery_black_24dp) {
                    finalRes = resolvedRes;
                }
            }
            kaiImageResource.setValue(finalRes);
        }, endAction);

        return endAction + 10000;
    }

    /**
     * Animación TUKAI: Secuencia de frames de movimiento con luciérnaga.
     * @return Duración exacta de la secuencia para loop infinito.
     */
    private long executeTuKaiAnimation() {
        final long frameTime = 220;
        final String[] frames = {"anim1", "anim2", "anim3", "anim4", "anim5", "anim6", "anim7", "anim6", "anim5", "anim4", "anim3", "anim2", "anim1"};
        final float[][] path = {
            {0, -100}, {20, -115}, {40, -130}, {60, -115}, {80, -100}, {60, -85}, {40, -70}, 
            {20, -85}, {0, -100}, {-20, -115}, {-40, -130}, {-20, -115}, {0, -100}
        };
        
        fireflyVisibility.setValue(View.VISIBLE);

        for (int i = 0; i < frames.length; i++) {
            final int idx = i;
            handler.postDelayed(() -> {
                kaiImageResource.setValue(ImageUi.getDrawable(frames[idx]));
                fireflyImageResource.setValue(ImageUi.getDrawable(idx % 2 == 0 ? "firefly_a" : "firefly_b"));
                fireflyTranslationX.setValue(path[idx][0]);
                fireflyTranslationY.setValue(path[idx][1]);
            }, i * frameTime);
        }

        return frames.length * frameTime; // Retorno exacto para loop sin pausas
    }

    private long executeCelebrationAnimation() {
        // Futura implementación
        return 3000;
    }

    private long executeSleepAnimation() {
        kaiImageResource.setValue(ImageUi.getDrawable("bb_dormido"));
        return 5000;
    }
}
