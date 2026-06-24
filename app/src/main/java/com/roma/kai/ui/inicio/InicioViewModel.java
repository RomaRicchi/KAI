package com.roma.kai.ui.inicio;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.R;
import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.remote.RetrofitClient;
import com.roma.kai.data.repository.InicioRepository;
import com.roma.kai.model.dto.HomeResponse;
import com.roma.kai.model.dto.KaiHomeSummary;
import com.roma.kai.session.SessionManager;
import com.roma.kai.utils.AnimationKai;
import com.roma.kai.utils.Event;
import com.roma.kai.utils.UiMessage;

public class InicioViewModel extends AndroidViewModel {
    private final InicioRepository inicioRepository;
    private final MutableLiveData<InicioUiState> inicioUiState = new MutableLiveData<>();
    private final MutableLiveData<Event<UiMessage>> eventUiMessage = new MutableLiveData<>();
    
    // Instancia de la nueva clase encargada de la animación
    private final AnimationKai animationKai = new AnimationKai();

    public InicioViewModel(@NonNull Application application) {
        super(application);
        inicioRepository = new InicioRepository(RetrofitClient.getService(application));
    }

    public LiveData<InicioUiState> getInicioUiState() { return inicioUiState; }
    public LiveData<Event<UiMessage>> getEventUiMessage() { return eventUiMessage; }
    
    // Exponemos los LiveData de la clase AnimationKai
    public LiveData<Integer> getKaiImageResource() { return animationKai.getKaiImageResource(); }
    public LiveData<Event<Boolean>> getPlaySoundEvent() { return animationKai.getPlaySoundEvent(); }
    public LiveData<Integer> getFireflyImageResource() { return animationKai.getFireflyImageResource(); }
    public LiveData<Float> getFireflyTranslationX() { return animationKai.getFireflyTranslationX(); }
    public LiveData<Float> getFireflyTranslationY() { return animationKai.getFireflyTranslationY(); }
    public LiveData<Integer> getFireflyVisibility() { return animationKai.getFireflyVisibility(); }

    // Getters para la segunda luciérnaga (Evolución)
    public LiveData<Integer> getFirefly2ImageResource() { return animationKai.getFirefly2ImageResource(); }
    public LiveData<Float> getFirefly2TranslationX() { return animationKai.getFirefly2TranslationX(); }
    public LiveData<Float> getFirefly2TranslationY() { return animationKai.getFirefly2TranslationY(); }
    public LiveData<Integer> getFirefly2Visibility() { return animationKai.getFirefly2Visibility(); }
    public LiveData<Float> getKaiScale() { return animationKai.getKaiScale(); }

    public void loadHomeView() {
        inicioUiState.setValue(InicioUiState.loading());

        inicioRepository.loadHomeView(new RepositoryCallback<HomeResponse>() {
            @Override
            public void onSuccess(HomeResponse data) {
                String xp = data.getXpTotal() + " XP";
                String racha = data.getRachaActual() + " Días";
                
                String kaiKey = null;
                String etapaActual = "cachorro";
                KaiHomeSummary kai = data.getEstadoKai();
                if (kai != null) {
                    kaiKey = (kai.getImageKai() != null) ? kai.getImageKai() : kai.getEstadoActual();
                    etapaActual = kai.getEtapaActual();
                }

                inicioUiState.setValue(new InicioUiState(
                        false,
                        true,
                        xp,
                        racha,
                        data.getMensajeMotivacional(),
                        kaiKey,
                        data.getHabitosDiarios()
                ));
                
                // --- Lógica de Evolución ---
                SessionManager session = SessionManager.getInstance(getApplication());
                String pending = session.getPendingEvolution();

                if (pending != null) {
                    // Si hay una evolución pendiente, disparamos la animación especial
                    AnimationKai.AnimationType type = "adulto".equalsIgnoreCase(pending) ? 
                            AnimationKai.AnimationType.EVOLUTION_2 : AnimationKai.AnimationType.EVOLUTION_1;
                    
                    animationKai.startAnimation(type);
                    session.clearPendingEvolution(); // Limpiar para que no se repita
                } else {
                    // Comportamiento normal: animación cíclica (solo si es cachorro)
                    animationKai.startAnimation(kaiKey, etapaActual, AnimationKai.AnimationType.HOME);
                }
            }

            @Override
            public void onError(String error) {
                inicioUiState.setValue(InicioUiState.error());
                eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        animationKai.stopAnimation();
    }
}
