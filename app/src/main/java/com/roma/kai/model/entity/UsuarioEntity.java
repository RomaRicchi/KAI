package com.roma.kai.model.entity;

import com.google.gson.annotations.SerializedName;

public class UsuarioEntity {
    private String id;
    private String nombre;
    private String email;
    @SerializedName("fecha_registro")
    private String fechaRegistro;
    @SerializedName("perfil_base")
    private String perfilBase;
    @SerializedName("etapa_kai")
    private String etapaKai;
    @SerializedName("racha_global")
    private int rachaGlobal;
    @SerializedName("dias_inactivo")
    private int diasInactivo;
    @SerializedName("imagen_perfil")
    private String imagenPerfil;

    public UsuarioEntity(String id, String nombre, String email, String fechaRegistro, String perfilBase, String etapaKai, int rachaGlobal, int diasInactivo, String imagenPerfil) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.fechaRegistro = fechaRegistro;
        this.perfilBase = perfilBase;
        this.etapaKai = etapaKai;
        this.rachaGlobal = rachaGlobal;
        this.diasInactivo = diasInactivo;
        this.imagenPerfil = imagenPerfil;
    }

    public UsuarioEntity() {}

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getPerfilBase() {
        return perfilBase;
    }

    public void setPerfilBase(String perfilBase) {
        this.perfilBase = perfilBase;
    }

    public String getEtapaKai() {
        return etapaKai;
    }

    public void setEtapaKai(String etapaKai) {
        this.etapaKai = etapaKai;
    }

    public int getRachaGlobal() {
        return rachaGlobal;
    }

    public void setRachaGlobal(int rachaGlobal) {
        this.rachaGlobal = rachaGlobal;
    }

    public int getDiasInactivo() {
        return diasInactivo;
    }

    public void setDiasInactivo(int diasInactivo) {
        this.diasInactivo = diasInactivo;
    }
}
