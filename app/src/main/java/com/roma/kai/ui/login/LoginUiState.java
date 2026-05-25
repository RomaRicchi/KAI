package com.roma.kai.ui.login;

import com.roma.kai.utils.Event;

public class LoginUiState {
    private final boolean loading;
    private final boolean success;
    private final Event<String> messageEvent;

    public LoginUiState(boolean loading, boolean success, Event<String> messageEvent) {
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
