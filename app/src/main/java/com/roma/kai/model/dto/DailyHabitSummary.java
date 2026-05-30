package com.roma.kai.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class DailyHabitSummary {
    @SerializedName("habito_usuario_id")
    private String id;
    private String nombre;
    private String categoria;
    private boolean completado;
    @SerializedName("imagen_habito")
    private String imagenHabito;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DailyHabitSummary that = (DailyHabitSummary) o;
        return completado == that.completado && Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && Objects.equals(categoria, that.categoria) && Objects.equals(imagenHabito, that.imagenHabito);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, categoria, completado, imagenHabito);
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public boolean isCompletado() {
        return completado;
    }

    public String getImagenHabito() {
        return imagenHabito;
    }
}
