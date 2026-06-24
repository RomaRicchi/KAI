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
        EVOLUTION_1,
        EVOLUTION_2
    }

    // --- LIVEDATA ---
    private final MutableLiveData<Integer> kaiImageResource = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> playSoundEvent = new MutableLiveData<>();
    private final MutableLiveData<Integer> fireflyImageResource = new MutableLiveData<>();
    private final MutableLiveData<Float> fireflyTranslationX = new MutableLiveData<>();
    private final MutableLiveData<Float> fireflyTranslationY = new MutableLiveData<>();
    private final MutableLiveData<Integer> fireflyVisibility = new MutableLiveData<>();

    // --- LIVEDATA PARA SEGUNDA LUCIÉRNAGA (EVOLUCIÓN) ---
    private final MutableLiveData<Integer> firefly2ImageResource = new MutableLiveData<>();
    private final MutableLiveData<Float> firefly2TranslationX = new MutableLiveData<>();
    private final MutableLiveData<Float> firefly2TranslationY = new MutableLiveData<>();
    private final MutableLiveData<Integer> firefly2Visibility = new MutableLiveData<>();

    // --- LIVEDATA PARA ESCALA DE KAI ---
    private final MutableLiveData<Float> kaiScale = new MutableLiveData<>(1.0f);

    // --- CONTROL ---
    private final Handler handler = new Handler(Looper.getMainLooper());
    private AnimationType currentAnimation = AnimationType.HOME;
    private String lastKaiKey = null;
    private String currentStage = null; // Se inicializa al recibir datos reales

    // --- BUCLE PRINCIPAL (DISPATCHER) ---
    private final Runnable mainAnimationLoop = new Runnable() {
        @Override
        public void run() {
            long cycleDuration;

            // Si no es cachorro, desactivamos animaciones cíclicas (HOME/TUKAI) y mostramos imagen estática
            if (!"cachorro".equalsIgnoreCase(currentStage) && (currentAnimation == AnimationType.HOME || currentAnimation == AnimationType.TUKAI)) {
                int resId = "adulto".equalsIgnoreCase(currentStage) ? ImageUi.getDrawable("kai12") : ImageUi.getDrawable("kai8");
                kaiImageResource.setValue(resId);
                return; // Detener el bucle
            }

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
                case EVOLUTION_1:
                    // La evolución es una TRANSICIÓN, no un bucle.
                    executeEvolution1Animation();
                    executeEvolutionFireflies(1200); // 4 frames * 300ms
                    return;
                case EVOLUTION_2:
                    executeEvolution2Animation();
                    executeEvolutionFireflies(900); // 3 frames * 300ms hasta kai5
                    return;
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

    // --- GETTERS SEGUNDA LUCIÉRNAGA ---
    public LiveData<Integer> getFirefly2ImageResource() { return firefly2ImageResource; }
    public LiveData<Float> getFirefly2TranslationX() { return firefly2TranslationX; }
    public LiveData<Float> getFirefly2TranslationY() { return firefly2TranslationY; }
    public LiveData<Integer> getFirefly2Visibility() { return firefly2Visibility; }

    public LiveData<Float> getKaiScale() { return kaiScale; }

    // --- API PÚBLICA ---

    public void startAnimation(String initialKey, String stage, AnimationType type) {
        stopAnimation(); 
        this.lastKaiKey = initialKey;
        this.currentStage = (stage != null) ? stage.toLowerCase() : "cachorro";
        this.currentAnimation = type;

        setupInitialFrame(type);
        handler.postDelayed(mainAnimationLoop, 500);
    }

    public void startAnimation(AnimationType type) {
        startAnimation(null, null, type);
    }

    public void stopAnimation() {
        handler.removeCallbacksAndMessages(null);
        fireflyVisibility.setValue(View.GONE);
        firefly2Visibility.setValue(View.GONE);
        kaiScale.setValue(1.0f);
    }

    public void updateBaseImage(String key) {
        this.lastKaiKey = key;
        if (currentAnimation == AnimationType.HOME && key != null) {
            kaiImageResource.setValue(ImageUi.getDrawable(key));
        }
    }

    private void setupInitialFrame(AnimationType type) {
        // Si ya ha evolucionado y es una animación cíclica (HOME/TUKAI), forzar imagen estática desde el primer frame
        if (currentStage != null && !"cachorro".equalsIgnoreCase(currentStage) && 
            (type == AnimationType.HOME || type == AnimationType.TUKAI)) {
            int resId = "adulto".equalsIgnoreCase(currentStage) ? ImageUi.getDrawable("kai12") : ImageUi.getDrawable("kai8");
            kaiImageResource.setValue(resId);
            return;
        }

        if (type == AnimationType.TUKAI) {
            kaiImageResource.setValue(ImageUi.getDrawable("anim1"));
        } else if (type == AnimationType.EVOLUTION_1) {
            kaiImageResource.setValue(ImageUi.getDrawable("kai1"));
        } else if (type == AnimationType.EVOLUTION_2) {
            kaiImageResource.setValue(ImageUi.getDrawable("kai8"));
        } else {
            kaiImageResource.setValue(ImageUi.getDrawable("kai_base"));
        }
    }

    // =========================================================================
    // IMPLEMENTACIÓN DE ANIMACIONES
    // =========================================================================

    /**
     * HOME: Comportamiento de mascota virtual (Bucle).
     */
    private long executeHomeAnimation() {
        final int baseFrame = ImageUi.getDrawable("kai_base");
        final int eyesClosedFrame = ImageUi.getDrawable("kai_ojos_cerrados");
        final int mouthOpenFrame = ImageUi.getDrawable("kai_boca_abierta");
        
        final long blinkDur = 100;
        final long meowDur = 800; 
        final long startDelay = 100;

        kaiImageResource.setValue(baseFrame);
        
        handler.postDelayed(() -> kaiImageResource.setValue(eyesClosedFrame), startDelay);
        handler.postDelayed(() -> kaiImageResource.setValue(baseFrame), startDelay + blinkDur);

        long startAction = startDelay + blinkDur + 50; 
        
        handler.postDelayed(() -> {
            playSoundEvent.setValue(new Event<>(true));
            handler.postDelayed(() -> kaiImageResource.setValue(mouthOpenFrame), 30);
        }, startAction);

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
     * TUKAI: Secuencia de movimiento con luciérnaga (Bucle).
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

        return frames.length * frameTime;
    }

    /**
     * EVOLUTION_1: Transición de Kai cachorro a Kai joven (SIN BUCLE).
     */
    private void executeEvolution1Animation() {
        final long frameTime = 300; 
        final String[] evolutionFrames = {"kai1", "kai2", "kai3", "kai4", "kai5", "kai6", "kai7", "kai8"};
        
        fireflyVisibility.setValue(View.GONE);
        firefly2Visibility.setValue(View.GONE);
        kaiScale.setValue(1.0f);

        for (int i = 0; i < evolutionFrames.length; i++) {
            final int idx = i;
            handler.postDelayed(() -> {
                kaiImageResource.setValue(ImageUi.getDrawable(evolutionFrames[idx]));
                
                // Efecto de EXPLOSIÓN para kai5
                if ("kai5".equals(evolutionFrames[idx])) {
                    final long explosionDur = 200; 
                    for (int step = 0; step <= 5; step++) {
                        float progress = step / 5f;
                        float scale = 0.2f + (0.9f * progress); 
                        handler.postDelayed(() -> kaiScale.setValue(scale), step * (explosionDur / 5));
                    }
                    handler.postDelayed(() -> kaiScale.setValue(1.0f), explosionDur + 50);
                } else if (idx < 4) {
                    kaiScale.setValue(1.0f);
                }
                
                if (idx == evolutionFrames.length - 1) {
                    lastKaiKey = evolutionFrames[idx]; 
                }
            }, i * frameTime);
        }
    }

    /**
     * EVOLUTION_2: Transición de Kai joven a Kai adulto (SIN BUCLE).
     */
    private void executeEvolution2Animation() {
        final long frameTime = 300;
        final String[] evolutionFrames = {"kai8", "kai9", "kai6", "kai5", "kai10", "kai11", "kai12"};
        
        fireflyVisibility.setValue(View.GONE);
        firefly2Visibility.setValue(View.GONE);
        kaiScale.setValue(1.0f);

        for (int i = 0; i < evolutionFrames.length; i++) {
            final int idx = i;
            handler.postDelayed(() -> {
                kaiImageResource.setValue(ImageUi.getDrawable(evolutionFrames[idx]));
                
                // Efecto de EXPLOSIÓN (Mismo efecto que EVOLUTION_1)
                if ("kai5".equals(evolutionFrames[idx])) {
                    final long explosionDur = 200;
                    for (int step = 0; step <= 5; step++) {
                        float progress = step / 5f;
                        float scale = 0.2f + (0.9f * progress); 
                        handler.postDelayed(() -> kaiScale.setValue(scale), step * (explosionDur / 5));
                    }
                    handler.postDelayed(() -> kaiScale.setValue(1.0f), explosionDur + 50);
                } else if (idx < 3) {
                    kaiScale.setValue(1.0f);
                }
                
                if (idx == evolutionFrames.length - 1) {
                    lastKaiKey = evolutionFrames[idx]; 
                }
            }, i * frameTime);
        }
    }

    /**
     * Secuencia mágica de luciérnagas para la Evolución.
     * @param totalDuration Tiempo hasta que aparezca el destello (kai5).
     */
    private void executeEvolutionFireflies(long totalDuration) {
        final long fireflySpeed = 100;
        final int totalSteps = (int) (totalDuration / fireflySpeed);
        
        fireflyVisibility.setValue(View.VISIBLE);

        for (int i = 0; i < totalSteps; i++) {
            final int step = i;
            handler.postDelayed(() -> {
                // Cálculo de trayectoria circular que desciende
                double angle = step * 0.5; // Velocidad de rotación
                double radius = 60 * (1.0 - (double) step / totalSteps) + 20; // Radio que se achica
                float x = (float) (Math.cos(angle) * radius);
                float y = (float) (Math.sin(angle) * radius * 0.5) - 90 + (90 * (float) step / totalSteps);

                // Luciérnaga 1
                fireflyImageResource.setValue(ImageUi.getDrawable(step % 2 == 0 ? "firefly_a" : "firefly_b"));
                fireflyTranslationX.setValue(x);
                fireflyTranslationY.setValue(y - 20);

                // Luciérnaga 2 (Aparece a mitad de camino y gira al revés)
                if (step > totalSteps / 4) {
                    firefly2Visibility.setValue(View.VISIBLE);
                    firefly2ImageResource.setValue(ImageUi.getDrawable(step % 2 == 0 ? "firefly_b" : "firefly_a"));
                    firefly2TranslationX.setValue(-x);
                    firefly2TranslationY.setValue(y - 10);
                }

                // Desvanecimiento final
                if (step == totalSteps - 1) {
                    handler.postDelayed(() -> {
                        fireflyVisibility.setValue(View.GONE);
                        firefly2Visibility.setValue(View.GONE);
                    }, fireflySpeed);
                }
            }, i * fireflySpeed);
        }
    }

    private long executeCelebrationAnimation() {
        return 3000;
    }

    private long executeSleepAnimation() {
        kaiImageResource.setValue(ImageUi.getDrawable("cachorro_dormido"));
        return 5000;
    }
}
