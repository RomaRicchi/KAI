package com.roma.kai.ui.habitos.detalleHabito;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.roma.kai.data.callback.RepositoryCallback;
import com.roma.kai.data.remote.RetrofitClient;
import com.roma.kai.data.repository.HabitosRepository;
import com.roma.kai.model.dto.HabitDetail;
import com.roma.kai.model.dto.HabitDetailResponse;
import com.roma.kai.model.dto.HabitRecord;
import com.roma.kai.utils.Event;
import com.roma.kai.utils.UiMessage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DetalleHabitoViewModel extends AndroidViewModel {
    private final HabitosRepository habitosRepository;
    private final MutableLiveData<DetalleHabitoUiState> uiState = new MutableLiveData<>();
    private final MutableLiveData<Event<UiMessage>> eventUiMessage = new MutableLiveData<>();
    private LocalDate currentViewDate = LocalDate.now();
    private HabitDetailResponse lastResponse;

    public DetalleHabitoViewModel(@NonNull Application application) {
        super(application);
        habitosRepository = new HabitosRepository(RetrofitClient.getService(application));
    }

    public LiveData<DetalleHabitoUiState> getUiState() { return uiState; }
    public LiveData<Event<UiMessage>> getEventUiMessage() { return eventUiMessage; }

    public void loadHabitDetail(String habitUserId) {
        uiState.setValue(DetalleHabitoUiState.loading());

        habitosRepository.getHabitDetail(habitUserId, new RepositoryCallback<HabitDetailResponse>() {
            @Override
            public void onSuccess(HabitDetailResponse data) {
                lastResponse = data;
                updateStateWithData(data, false);
            }

            @Override
            public void onError(String error) {
                uiState.setValue(new DetalleHabitoUiState(false, "", "", "", "", "", "", null, false, false, error));
                eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
            }
        });
    }

    public void moveMonth(int offset) {
        if (lastResponse != null) {
            currentViewDate = currentViewDate.plusMonths(offset);
            updateStateWithData(lastResponse, false);
        }
    }

    public void deactivateHabit(String habitUserId) {
        uiState.setValue(copyStateWithLoading(true));

        habitosRepository.deactivateHabit(habitUserId, new RepositoryCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                uiState.setValue(copyStateWithLoading(false, true));
                eventUiMessage.setValue(new Event<>(new UiMessage("Hábito desactivado", UiMessage.Type.SUCCESS)));
            }

            @Override
            public void onError(String error) {
                updateStateWithData(lastResponse, false);
                eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
            }
        });
    }

    public void completeHabit(String habitUserId) {
        uiState.setValue(copyStateWithLoading(true));

        habitosRepository.completeHabit(habitUserId, new RepositoryCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                loadHabitDetail(habitUserId); // Refresh data
                eventUiMessage.setValue(new Event<>(new UiMessage("¡Hábito completado!", UiMessage.Type.SUCCESS)));
            }

            @Override
            public void onError(String error) {
                updateStateWithData(lastResponse, false);
                eventUiMessage.setValue(new Event<>(new UiMessage(error, UiMessage.Type.ERROR)));
            }
        });
    }

    private void updateStateWithData(HabitDetailResponse response, boolean isDeactivated) {
        HabitDetail habit = response.getHabit();
        List<HabitRecord> records = response.getRegistros();

        Map<String, Boolean> recordsMap = new HashMap<>();
        long completedCount = 0;
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (records != null) {
            for (HabitRecord record : records) {
                if (record.isCompletado()) completedCount++;
                String dateKey = record.getFecha();
                if (dateKey != null && dateKey.contains("T")) {
                    dateKey = dateKey.split("T")[0];
                }
                recordsMap.put(dateKey, record.isCompletado());
            }
        }

        String monthName = currentViewDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        String header = monthName.substring(0, 1).toUpperCase() + monthName.substring(1) + " " + currentViewDate.getYear();

        List<CalendarDayUiModel> calendarDays = new ArrayList<>();
        LocalDate firstOfMonth = currentViewDate.withDayOfMonth(1);
        int daysInMonth = currentViewDate.lengthOfMonth();
        int offset = firstOfMonth.getDayOfWeek().getValue() - 1;

        for (int i = 0; i < offset; i++) {
            calendarDays.add(CalendarDayUiModel.createEmpty());
        }

        LocalDate today = LocalDate.now();
        String todayStr = today.format(parser);

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = currentViewDate.withDayOfMonth(day);
            String dateStr = date.format(parser);
            boolean isCompleted = Boolean.TRUE.equals(recordsMap.get(dateStr));
            calendarDays.add(new CalendarDayUiModel(String.valueOf(day), isCompleted, date.equals(today), false));
        }

        uiState.setValue(new DetalleHabitoUiState(
                false,
                habit.getNombre(),
                habit.getCategoria(),
                String.valueOf(habit.getRachaActual()),
                String.valueOf(completedCount),
                habit.getCategoria(),
                header,
                calendarDays,
                Boolean.TRUE.equals(recordsMap.get(todayStr)),
                isDeactivated,
                null
        ));
    }

    private DetalleHabitoUiState copyStateWithLoading(boolean loading) {
        return copyStateWithLoading(loading, false);
    }

    private DetalleHabitoUiState copyStateWithLoading(boolean loading, boolean deactivated) {
        DetalleHabitoUiState current = uiState.getValue();
        if (current == null) return DetalleHabitoUiState.loading();
        return new DetalleHabitoUiState(
                loading,
                current.getHabitName(),
                current.getCategoryName(),
                current.getCurrentStreak(),
                current.getSuccessfulDaysCount(),
                current.getCategoryImageKey(),
                current.getMonthYearHeader(),
                current.getCalendarDays(),
                current.isTodayCompleted(),
                deactivated,
                current.getErrorMessage()
        );
    }
}
