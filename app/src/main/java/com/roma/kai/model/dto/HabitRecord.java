package com.roma.kai.model.dto;

public class HabitRecord {
    private String fecha;
    private boolean completado;

    public HabitRecord(String fecha, boolean completado) {
        this.fecha = fecha;
        this.completado = completado;
    }

    public String getFecha() {
        return fecha;
    }

    public boolean isCompletado() {
        return completado;
    }
}
