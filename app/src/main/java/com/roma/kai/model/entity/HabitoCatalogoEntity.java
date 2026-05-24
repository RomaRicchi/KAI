package com.roma.kai.model.entity;

import com.google.gson.annotations.SerializedName;

public class HabitoCatalogoEntity {
    //habitos_catalogo
    private String id;
    private String nombre;
    private String descripcion;
    private String categoria;
    @SerializedName("tipo_cuidado")
    private String tipoCuidado;
    private String dificultad;
    @SerializedName("xp_base")
    private int xpBase;
    @SerializedName("es_premium")
    private boolean esPremium;
    private boolean activo;
    @SerializedName("image_habito")
    private String imageHabito;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("categoria_xp_id")
    private String categoriaXpId;

    public HabitoCatalogoEntity(String id, String nombre, String descripcion, String categoria, String tipoCuidado, String dificultad, int xpBase, boolean esPremium, boolean activo, String imageHabito, String createdAt, String categoriaXpId) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.tipoCuidado = tipoCuidado;
        this.dificultad = dificultad;
        this.xpBase = xpBase;
        this.esPremium = esPremium;
        this.activo = activo;
        this.imageHabito = imageHabito;
        this.createdAt = createdAt;
        this.categoriaXpId = categoriaXpId;
    }

    public HabitoCatalogoEntity() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipoCuidado() {
        return tipoCuidado;
    }

    public void setTipoCuidado(String tipoCuidado) {
        this.tipoCuidado = tipoCuidado;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public int getXpBase() {
        return xpBase;
    }

    public void setXpBase(int xpBase) {
        this.xpBase = xpBase;
    }

    public boolean isEsPremium() {
        return esPremium;
    }

    public void setEsPremium(boolean esPremium) {
        this.esPremium = esPremium;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getImageHabito() {
        return imageHabito;
    }

    public void setImageHabito(String imageHabito) {
        this.imageHabito = imageHabito;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCategoriaXpId() {
        return categoriaXpId;
    }

    public void setCategoriaXpId(String categoriaXpId) {
        this.categoriaXpId = categoriaXpId;
    }
}
