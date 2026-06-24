package com.roma.kai.ui.kai;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.remote.RetrofitClient;
import com.roma.kai.data.repository.KaiRepository;
import com.roma.kai.model.dto.KaiDashboarResponse;
import com.roma.kai.model.dto.KaiAttributeDto;
import com.roma.kai.utils.AnimationKai;
import com.roma.kai.utils.AppMapper;
import com.roma.kai.utils.Event;
import com.roma.kai.utils.UiMessage;

import java.util.ArrayList;
import java.util.List;

public class TuKaiViewModel extends AndroidViewModel {
    private final KaiRepository kaiRepository;
    private final MutableLiveData<TuKaiUiState> tuKaiUiState = new MutableLiveData<>();
    private final MutableLiveData<Event<UiMessage>> eventUiMessage = new MutableLiveData<>();
    
    // Instancia de AnimationKai para manejar las animaciones en esta vista
    private final AnimationKai animationKai = new AnimationKai();

    public TuKaiViewModel(@NonNull Application application) {
        super(application);
        kaiRepository = new KaiRepository(RetrofitClient.getService(application));
    }

    public LiveData<TuKaiUiState> getTuKaiUiState() { return tuKaiUiState; }
    public LiveData<Event<UiMessage>> getEventUiMessage() { return eventUiMessage; }

    // Getters delegados para AnimationKai
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

    public void loadTuKaiData() {
        tuKaiUiState.setValue(TuKaiUiState.loading());

        kaiRepository.loadTuKaiView(new RepositoryCallback<KaiDashboarResponse>() {
            @Override
            public void onSuccess(KaiDashboarResponse data) {
                tuKaiUiState.setValue(new TuKaiUiState(
                        false,
                        true,
                        data.getEstadoKai(),
                        data.getMensajeEmocional(),
                        data.getAtributos(),
                        data.getCategoriaDominante(),
                        data.getCategoriaMenosDominante(),
                        data.getProgresoDiario()
                ));

                // Iniciar animación de Kai (Usando el nuevo motor de animaciones)
                if (data.getEstadoKai() != null) {
                    animationKai.startAnimation(
                            data.getEstadoKai().getEstadoActual(), 
                            data.getEstadoKai().getEtapaActual(),
                            AnimationKai.AnimationType.TUKAI
                    );
                }
            }

            @Override
            public void onError(String error) {
                tuKaiUiState.setValue(TuKaiUiState.error());
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
