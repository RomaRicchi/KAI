package com.roma.kai.utils;

public class UiMessage {
    public enum Type {
        SUCCESS,
        ERROR,
        INFO,
        WARNING
    }

    private final String message;

    private final Type type;

    public UiMessage(
            String message,
            Type type
    ) {

        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public Type getType() {
        return type;
    }
}
