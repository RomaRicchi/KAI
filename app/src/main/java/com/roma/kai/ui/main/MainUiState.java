package com.roma.kai.ui.main;

import com.roma.kai.utils.Event;

public class MainUiState {
    private final boolean loading;
    private final boolean success;
    private final Event<String> messageEvent;

    public MainUiState(boolean loading, boolean success, Event<String> messageEvent) {
        this.loading = loading;
        this.success = success;
        this.messageEvent = messageEvent;
    }

    public boolean isLoading() {
        return loading;
    }

    public boolean isSuccess() {
        return success;
    }

    public Event<String> getMessageEvent() {
        return messageEvent;
    }
}
