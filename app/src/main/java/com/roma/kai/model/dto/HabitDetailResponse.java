package com.roma.kai.model.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HabitDetailResponse {
    @SerializedName("habito_usuario")
    private HabitDetail habit;

    @SerializedName("registros_habito")
    private List<HabitRecord> registros;

    public HabitDetail getHabit() {
        return habit;
    }

    public List<HabitRecord> getRegistros() {
        return registros;
    }
}
