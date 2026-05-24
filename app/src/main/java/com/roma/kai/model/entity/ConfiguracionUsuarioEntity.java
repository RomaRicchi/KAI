package com.roma.kai.model.entity;

import com.google.gson.annotations.SerializedName;

public class ConfiguracionUsuarioEntity {
    //configuracion_usuario
    private String id;
    @SerializedName("usuario_id")
    private String usuarioId;
    @SerializedName("notificaciones_activas")
    private boolean notificacionesActivas;
    @SerializedName("sonidos_activos")
    private boolean sonidosActivos;
    @SerializedName("mostrar_rachas")
    private boolean mostrarRachas;
    @SerializedName("modo_discreto")
    private boolean modoDiscreto;
    @SerializedName("intensidad_kai")
    private String intensidadKai;
    @SerializedName("horario_recordatorio")
    private String horarioRecordatorio;
    @SerializedName("bloquear_con_pin")
    private boolean bloquearConPin;
    @SerializedName("permitir_mensajes_emocionales")
    private boolean permitirMensajesEmocionales;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    public ConfiguracionUsuarioEntity(String id, String usuarioId, boolean notificacionesActivas, boolean sonidosActivos, boolean mostrarRachas, boolean modoDiscreto, String intensidadKai, String horarioRecordatorio, boolean bloquearConPin, boolean permitirMensajesEmocionales, String createdAt, String updatedAt) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.notificacionesActivas = notificacionesActivas;
        this.sonidosActivos = sonidosActivos;
        this.mostrarRachas = mostrarRachas;
        this.modoDiscreto = modoDiscreto;
        this.intensidadKai = intensidadKai;
        this.horarioRecordatorio = horarioRecordatorio;
        this.bloquearConPin = bloquearConPin;
        this.permitirMensajesEmocionales = permitirMensajesEmocionales;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ConfiguracionUsuarioEntity() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public boolean isNotificacionesActivas() {
        return notificacionesActivas;
    }

    public void setNotificacionesActivas(boolean notificacionesActivas) {
        this.notificacionesActivas = notificacionesActivas;
    }

    public boolean isSonidosActivos() {
        return sonidosActivos;
    }

    public void setSonidosActivos(boolean sonidosActivos) {
        this.sonidosActivos = sonidosActivos;
    }

    public boolean isMostrarRachas() {
        return mostrarRachas;
    }

    public void setMostrarRachas(boolean mostrarRachas) {
        this.mostrarRachas = mostrarRachas;
    }

    public boolean isModoDiscreto() {
        return modoDiscreto;
    }

    public void setModoDiscreto(boolean modoDiscreto) {
        this.modoDiscreto = modoDiscreto;
    }

    public String getIntensidadKai() {
        return intensidadKai;
    }

    public void setIntensidadKai(String intensidadKai) {
        this.intensidadKai = intensidadKai;
    }

    public String getHorarioRecordatorio() {
        return horarioRecordatorio;
    }

    public void setHorarioRecordatorio(String horarioRecordatorio) {
        this.horarioRecordatorio = horarioRecordatorio;
    }

    public boolean isBloquearConPin() {
        return bloquearConPin;
    }

    public void setBloquearConPin(boolean bloquearConPin) {
        this.bloquearConPin = bloquearConPin;
    }

    public boolean isPermitirMensajesEmocionales() {
        return permitirMensajesEmocionales;
    }

    public void setPermitirMensajesEmocionales(boolean permitirMensajesEmocionales) {
        this.permitirMensajesEmocionales = permitirMensajesEmocionales;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
