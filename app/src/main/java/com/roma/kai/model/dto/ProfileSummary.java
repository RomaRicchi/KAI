package com.roma.kai.model.dto;

import com.google.gson.annotations.SerializedName;

public class ProfileSummary {
    @SerializedName("xp_total")
    private int xpTotal;
    @SerializedName("racha_global")
    private int rachaGlobal;
    @SerializedName("dias_inactivo")
    private int diasInactivo;

    public int getXpTotal() {
        return xpTotal;
    }

    public int getRachaGlobal() {
        return rachaGlobal;
    }

    public int getDiasInactivo() {
        return diasInactivo;
    }
}
