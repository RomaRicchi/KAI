package com.roma.kai.model.entity;

import com.google.gson.annotations.SerializedName;

public class RegistroHabitoEntity {
    //registros_habito
    private String id;
    @SerializedName("usuario_id")
    private String usuarioId;
    @SerializedName("habito_usuario_id")
    private String habitoUsuarioId;
    private String fecha;
    private boolean completado;
    @SerializedName("valor_registrado")
    private String valorRegistrado;
    @SerializedName("xp_ganada")
    private int xpGanada;
    @SerializedName("created_at")
    private String createdAt;

    public RegistroHabitoEntity(String id, String usuarioId, String habitoUsuarioId, String fecha, boolean completado, String valorRegistrado, int xpGanada, String createdAt) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.habitoUsuarioId = habitoUsuarioId;
        this.fecha = fecha;
        this.completado = completado;
        this.valorRegistrado = valorRegistrado;
        this.xpGanada = xpGanada;
        this.createdAt = createdAt;
    }

    public RegistroHabitoEntity() {}

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

    public String getHabitoUsuarioId() {
        return habitoUsuarioId;
    }

    public void setHabitoUsuarioId(String habitoUsuarioId) {
        this.habitoUsuarioId = habitoUsuarioId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public String getValorRegistrado() {
        return valorRegistrado;
    }

    public void setValorRegistrado(String valorRegistrado) {
        this.valorRegistrado = valorRegistrado;
    }

    public int getXpGanada() {
        return xpGanada;
    }

    public void setXpGanada(int xpGanada) {
        this.xpGanada = xpGanada;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
