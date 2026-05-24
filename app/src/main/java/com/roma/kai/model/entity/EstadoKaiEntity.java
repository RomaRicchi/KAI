package com.roma.kai.model.entity;

import com.google.gson.annotations.SerializedName;

public class EstadoKaiEntity {
    //estado_kai
    private String id;
    @SerializedName("usuario_id")
    private String usuarioId;
    @SerializedName("estado_actual")
    private String estadoActual;
    @SerializedName("etapa_actual")
    private String estapaActual;
    private int energia;
    @SerializedName("imagen_kai")
    private String imageKai;
    @SerializedName("ultimo_mensaje")
    private String ultimoMensaje;
    @SerializedName("ultima_interaccion")
    private String ultimaInteraccion;
    @SerializedName("dias_sin_actividad")
    private int diasSinActividad;
    @SerializedName("modo_recuperacion")
    private boolean modoRecuperacion;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("modo_actual")
    private String modoActual;
    @SerializedName("atributo_dominante_id")
    private String atributoDominanteId;
    @SerializedName("nivel_vinculo")
    private int nivelVinculo;
    @SerializedName("ultima_evolucion")
    private String ultimaEvolucion;

    public EstadoKaiEntity(String id, String usuarioId, String estadoActual, String estapaActual, int energia, String imageKai, String ultimoMensaje, String ultimaInteraccion, int diasSinActividad, boolean modoRecuperacion, String createdAt, String updatedAt, String modoActual, String atributoDominanteId, int nivelVinculo, String ultimaEvolucion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.estadoActual = estadoActual;
        this.estapaActual = estapaActual;
        this.energia = energia;
        this.imageKai = imageKai;
        this.ultimoMensaje = ultimoMensaje;
        this.ultimaInteraccion = ultimaInteraccion;
        this.diasSinActividad = diasSinActividad;
        this.modoRecuperacion = modoRecuperacion;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.modoActual = modoActual;
        this.atributoDominanteId = atributoDominanteId;
        this.nivelVinculo = nivelVinculo;
        this.ultimaEvolucion = ultimaEvolucion;
    }

    public EstadoKaiEntity() {}

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

    public String getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }

    public String getEstapaActual() {
        return estapaActual;
    }

    public void setEstapaActual(String estapaActual) {
        this.estapaActual = estapaActual;
    }

    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public String getImageKai() {
        return imageKai;
    }

    public void setImageKai(String imageKai) {
        this.imageKai = imageKai;
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }

    public void setUltimoMensaje(String ultimoMensaje) {
        this.ultimoMensaje = ultimoMensaje;
    }

    public String getUltimaInteraccion() {
        return ultimaInteraccion;
    }

    public void setUltimaInteraccion(String ultimaInteraccion) {
        this.ultimaInteraccion = ultimaInteraccion;
    }

    public int getDiasSinActividad() {
        return diasSinActividad;
    }

    public void setDiasSinActividad(int diasSinActividad) {
        this.diasSinActividad = diasSinActividad;
    }

    public boolean isModoRecuperacion() {
        return modoRecuperacion;
    }

    public void setModoRecuperacion(boolean modoRecuperacion) {
        this.modoRecuperacion = modoRecuperacion;
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

    public String getModoActual() {
        return modoActual;
    }

    public void setModoActual(String modoActual) {
        this.modoActual = modoActual;
    }

    public String getAtributoDominanteId() {
        return atributoDominanteId;
    }

    public void setAtributoDominanteId(String atributoDominanteId) {
        this.atributoDominanteId = atributoDominanteId;
    }

    public int getNivelVinculo() {
        return nivelVinculo;
    }

    public void setNivelVinculo(int nivelVinculo) {
        this.nivelVinculo = nivelVinculo;
    }

    public String getUltimaEvolucion() {
        return ultimaEvolucion;
    }

    public void setUltimaEvolucion(String ultimaEvolucion) {
        this.ultimaEvolucion = ultimaEvolucion;
    }
}
