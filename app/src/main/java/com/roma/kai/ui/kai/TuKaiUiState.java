package com.roma.kai.ui.kai;

import java.util.List;

public class TuKaiUiState {
    private final boolean isLoading;
    private final boolean isSuccess;
    private final String stage;
    private final int energy;
    private final String energyDesc;
    private final String personalityTitle;
    private final String emotionalMessage;
    private final String kaiImageKey;
    private final List<String> attributeLabels;
    private final List<Float> attributeValues;
    private final String categoryPredominante;
    private final String categoryPredominanteKey;
    private final String categoryMenosAvanzada;

    public TuKaiUiState(boolean isLoading, boolean isSuccess, String stage, int energy, String energyDesc, String personalityTitle, String emotionalMessage, String kaiImageKey, List<String> attributeLabels, List<Float> attributeValues, String categoryPredominante, String categoryPredominanteKey, String categoryMenosAvanzada) {
        this.isLoading = isLoading;
        this.isSuccess = isSuccess;
        this.stage = stage;
        this.energy = energy;
        this.energyDesc = energyDesc;
        this.personalityTitle = personalityTitle;
        this.emotionalMessage = emotionalMessage;
        this.kaiImageKey = kaiImageKey;
        this.attributeLabels = attributeLabels;
        this.attributeValues = attributeValues;
        this.categoryPredominante = categoryPredominante;
        this.categoryPredominanteKey = categoryPredominanteKey;
        this.categoryMenosAvanzada = categoryMenosAvanzada;
    }

    public static TuKaiUiState loading() {
        return new TuKaiUiState(true, false, "", 0, "", "", "", null, null, null, "", "", "");
    }

    public static TuKaiUiState error() {
        return new TuKaiUiState(false, false, "", 0, "", "", "", null, null, null, "", "", "");
    }

    public boolean isLoading() { return isLoading; }
    public boolean isSuccess() { return isSuccess; }
    public String getStage() { return stage; }
    public int getEnergy() { return energy; }
    public String getEnergyDesc() { return energyDesc; }
    public String getPersonalityTitle() { return personalityTitle; }
    public String getEmotionalMessage() { return emotionalMessage; }
    public String getKaiImageKey() { return kaiImageKey; }
    public List<String> getAttributeLabels() { return attributeLabels; }
    public List<Float> getAttributeValues() { return attributeValues; }
    public String getCategoryPredominante() { return categoryPredominante; }
    public String getCategoryPredominanteKey() { return categoryPredominanteKey; }
    public String getCategoryMenosAvanzada() { return categoryMenosAvanzada; }
}
