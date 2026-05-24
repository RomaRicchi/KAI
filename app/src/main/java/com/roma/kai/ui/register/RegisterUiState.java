package com.roma.kai.ui.register;

import com.roma.kai.utils.Event;

public class RegisterUiState {
    private final boolean loading;
    private final boolean success;
    private final Event<String> messageEvent;

    public RegisterUiState(boolean loading, boolean success, Event<String> messageEvent) {
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
