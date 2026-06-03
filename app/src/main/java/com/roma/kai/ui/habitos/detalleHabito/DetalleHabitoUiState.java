package com.roma.kai.ui.habitos.detalleHabito;

import java.util.List;

public class DetalleHabitoUiState {
    private final boolean isLoading;
    private final String habitName;
    private final String categoryName;
    private final String currentStreak;
    private final String successfulDaysCount;
    private final String categoryImageKey;
    private final String monthYearHeader;
    private final List<CalendarDayUiModel> calendarDays;
    private final boolean isTodayCompleted;
    private final boolean isDeactivated;
    private final String errorMessage;

    public DetalleHabitoUiState(
            boolean isLoading,
            String habitName,
            String categoryName,
            String currentStreak,
            String successfulDaysCount,
            String categoryImageKey,
            String monthYearHeader,
            List<CalendarDayUiModel> calendarDays,
            boolean isTodayCompleted,
            boolean isDeactivated,
            String errorMessage
    ) {
        this.isLoading = isLoading;
        this.habitName = habitName;
        this.categoryName = categoryName;
        this.currentStreak = currentStreak;
        this.successfulDaysCount = successfulDaysCount;
        this.categoryImageKey = categoryImageKey;
        this.monthYearHeader = monthYearHeader;
        this.calendarDays = calendarDays;
        this.isTodayCompleted = isTodayCompleted;
        this.isDeactivated = isDeactivated;
        this.errorMessage = errorMessage;
    }

    // Getters
    public boolean isLoading() { return isLoading; }
    public String getHabitName() { return habitName; }
    public String getCategoryName() { return categoryName; }
    public String getCurrentStreak() { return currentStreak; }
    public String getSuccessfulDaysCount() { return successfulDaysCount; }
    public String getCategoryImageKey() { return categoryImageKey; }
    public String getMonthYearHeader() { return monthYearHeader; }
    public List<CalendarDayUiModel> getCalendarDays() { return calendarDays; }
    public boolean isTodayCompleted() { return isTodayCompleted; }
    public boolean isDeactivated() { return isDeactivated; }
    public String getErrorMessage() { return errorMessage; }

    // Helper for initial/loading state
    public static DetalleHabitoUiState loading() {
        return new DetalleHabitoUiState(true, "", "", "", "", "", "", null, false, false, null);
    }
}
