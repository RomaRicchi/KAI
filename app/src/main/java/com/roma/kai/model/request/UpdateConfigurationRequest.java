package com.roma.kai.model.request;

import com.google.gson.annotations.SerializedName;

public class UpdateConfigurationRequest {
    @SerializedName("notificaciones_activas")
    private final Boolean notificacionesActivas;
    @SerializedName("sonidos_activos")
    private final Boolean sonidosActivos;
    @SerializedName("mostrar_rachas")
    private final Boolean mostrarRachas;
    @SerializedName("modo_discreto")
    private final Boolean modoDiscreto;
    @SerializedName("intensidad_kai")
    private final String intensidadKai;
    @SerializedName("horario_recordatorio")
    private final String horarioRecordatorio;
    @SerializedName("bloquear_con_pin")
    private final Boolean bloquearConPin;
    @SerializedName("permitir_mensajes_emocionales")
    private final Boolean permitirMensajesEmocionales;

    public UpdateConfigurationRequest(
            Boolean notificacionesActivas,
            Boolean sonidosActivos,
            Boolean mostrarRachas,
            Boolean modoDiscreto,
            String intensidadKai,
            String horarioRecordatorio,
            Boolean bloquearConPin,
            Boolean permitirMensajesEmocionales
    ) {
        this.notificacionesActivas = notificacionesActivas;
        this.sonidosActivos = sonidosActivos;
        this.mostrarRachas = mostrarRachas;
        this.modoDiscreto = modoDiscreto;
        this.intensidadKai = intensidadKai;
        this.horarioRecordatorio = horarioRecordatorio;
        this.bloquearConPin = bloquearConPin;
        this.permitirMensajesEmocionales = permitirMensajesEmocionales;
    }
}
