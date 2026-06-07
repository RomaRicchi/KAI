package com.roma.kai.ui.kai;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.remote.RetrofitClient;
import com.roma.kai.data.repository.KaiRepository;
import com.roma.kai.model.dto.HomeResponse;
import com.roma.kai.model.dto.KaiDashboarResponse;
import com.roma.kai.model.dto.KaiHomeSummary;
import com.roma.kai.model.dto.KaiAttributeDto;
import com.roma.kai.model.dto.KaiStateResponse;
import com.roma.kai.utils.AppMapper;
import com.roma.kai.utils.Event;
import com.roma.kai.utils.UiMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TuKaiViewModel extends AndroidViewModel {
    private final KaiRepository kaiRepository;
    private final MutableLiveData<TuKaiUiState> tuKaiUiState = new MutableLiveData<>();
    private final MutableLiveData<Event<UiMessage>> eventUiMessage = new MutableLiveData<>();

    public TuKaiViewModel(@NonNull Application application) {
        super(application);
        kaiRepository = new KaiRepository(RetrofitClient.getService(application));
    }

    public LiveData<TuKaiUiState> getTuKaiUiState() { return tuKaiUiState; }
    public LiveData<Event<UiMessage>> getEventUiMessage() { return eventUiMessage; }

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



//                KaiStateResponse kai = data.getEstadoKai();
//                if (kai == null) {
//                    tuKaiUiState.setValue(TuKaiUiState.error());
//                    return;
//                }
//
//                // Lista completa de atributos según la imagen del usuario
//                List<KaiAttributeDto> allAttributes = Arrays.asList(
//                        new KaiAttributeDto("Fuerza", 85),
//                        new KaiAttributeDto("Resistencia", 60),
//                        new KaiAttributeDto("Vitalidad", 40),
//                        new KaiAttributeDto("Conciencia", 0), // Este se filtrará
//                        new KaiAttributeDto("Sabiduria", 78),
//                        new KaiAttributeDto("Equilibrio", 0), // Este se filtrará
//                        new KaiAttributeDto("Disciplina", 50),
//                        new KaiAttributeDto("Vínculo", 90)
//                );
//
//                // Filtrar los que tengan XP > 0
//                List<KaiAttributeDto> filteredAttributes = new ArrayList<>();
//                for (KaiAttributeDto attr : allAttributes) {
//                    if (attr.getValor() > 0) {
//                        filteredAttributes.add(attr);
//                    }
//                }
//
//                // Si hay menos de 3, no se puede hacer un radar coherente,
//                // así que podríamos mostrar al menos los 3 principales o todos los que tengan XP.
//                // En este caso, si hay al menos 3, procedemos.
//
//                List<String> labels = new ArrayList<>();
//                List<Float> values = new ArrayList<>();
//
//                for (KaiAttributeDto attr : filteredAttributes) {
//                    labels.add(attr.getNombre());
//                    values.add((float) attr.getValor());
//                }
//
//                // Encontrar predominante y menos avanzada
//                Collections.sort(filteredAttributes, new Comparator<KaiAttributeDto>() {
//                    @Override
//                    public int compare(KaiAttributeDto o1, KaiAttributeDto o2) {
//                        return Integer.compare(o2.getValor(), o1.getValor());
//                    }
//                });
//
//                String predName = filteredAttributes.isEmpty() ? "Ninguna" : filteredAttributes.get(0).getNombre();
//                String menosName = filteredAttributes.isEmpty() ? "Vitalidad" : filteredAttributes.get(filteredAttributes.size() - 1).getNombre();
//                String menosMsg = "Recuerda trabajar en tu " + menosName;
//
//                // Mapeo lógico de Atributo -> Imagen de Personalidad (Drawable) corregido
//                String predKey;
//                switch (predName) {
//                    case "Fuerza": predKey = "movimiento"; break;
//                    case "Conciencia": predKey = "sabiduria"; break;
//                    case "Resistencia": predKey = "constancia"; break;
//                    case "Vínculo": predKey = "conexion"; break;
//                    case "Disciplina": predKey = "disciplina"; break;
//                    case "Equilibrio": predKey = "equilibrio"; break;
//                    case "Vitalidad": predKey = "vitalidad"; break;
//                    case "Sabiduria":
//                    case "Sabiduría": predKey = "sabiduria"; break;
//                    default:
//                        predKey = predName.toLowerCase()
//                                .replace("í", "i")
//                                .replace("ú", "u")
//                                .replace("ó", "o")
//                                .replace("á", "a")
//                                .replace("é", "e");
//                        break;
//                }
//
//                String stage = kai.getEtapaActual() != null ? kai.getEtapaActual() : "Cachorro";
//                if (stage.equalsIgnoreCase("bebé") || stage.equalsIgnoreCase("bebe")) {
//                    stage = "Cachorro";
//                }
//
//                tuKaiUiState.setValue(new TuKaiUiState(
//                        false,
//                        true,
//                        stage,
//                        kai.getEnergia(),
//                        kai.getEnergia() + "/100 - Enérgico (Mejora con hábitos)",
//                        "Personalidad " + predName,
//                        "¡Me siento curioso y listo para aprender contigo!",
//                        kai.getImageKai() != null ? kai.getImageKai() : kai.getEstadoActual(),
//                        labels,
//                        values,
//                        predName, // categoryPredominante ahora es solo el nombre
//                        predKey,
//                        menosMsg // Mensaje amigable
//                ));
            }

            @Override
            public void onError(String error) {
                tuKaiUiState.setValue(TuKaiUiState.error());
                eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
            }
        });
    }
}
