package com.roma.kai.model.dto;

import com.google.gson.annotations.SerializedName;

public class CompleteHabitResponse {
    @SerializedName("habito_usuario_id")
    private String habitoUsuarioId;
    @SerializedName("xp_ganada")
    private int xpGanada;
    private boolean evoluciono;
    @SerializedName("etapa_anterior")
    private String etapaAnterior;
    @SerializedName("etapa_actual")
    private String etapaActual;
    @SerializedName("evento_evolucion")
    private EvolutionEventResponse eventoEvolucion;

    public String getHabitoUsuarioId() { return habitoUsuarioId; }
    public void setHabitoUsuarioId(String habitoUsuarioId) { this.habitoUsuarioId = habitoUsuarioId; }
    public int getXpGanada() { return xpGanada; }
    public void setXpGanada(int xpGanada) { this.xpGanada = xpGanada; }
    public boolean isEvoluciono() { return evoluciono; }
    public void setEvoluciono(boolean evoluciono) { this.evoluciono = evoluciono; }
    public String getEtapaAnterior() { return etapaAnterior; }
    public void setEtapaAnterior(String etapaAnterior) { this.etapaAnterior = etapaAnterior; }
    public String getEtapaActual() { return etapaActual; }
    public void setEtapaActual(String etapaActual) { this.etapaActual = etapaActual; }
    public EvolutionEventResponse getEventoEvolucion() { return eventoEvolucion; }
    public void setEventoEvolucion(EvolutionEventResponse eventoEvolucion) { this.eventoEvolucion = eventoEvolucion; }
}
