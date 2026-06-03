package com.roma.kai.ui.habitos.detalleHabito;

public class CalendarDayUiModel {
    private final String dayNumber;
    private final boolean isCompleted;
    private final boolean isToday;
    private final boolean isEmpty;

    public CalendarDayUiModel(String dayNumber, boolean isCompleted, boolean isToday, boolean isEmpty) {
        this.dayNumber = dayNumber;
        this.isCompleted = isCompleted;
        this.isToday = isToday;
        this.isEmpty = isEmpty;
    }

    public String getDayNumber() { return dayNumber; }
    public boolean isCompleted() { return isCompleted; }
    public boolean isToday() { return isToday; }
    public boolean isEmpty() { return isEmpty; }

    public static CalendarDayUiModel createEmpty() {
        return new CalendarDayUiModel("", false, false, true);
    }
}
