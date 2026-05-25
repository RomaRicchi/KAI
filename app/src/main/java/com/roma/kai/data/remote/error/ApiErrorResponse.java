package com.roma.kai.data.remote.error;

public class ApiErrorResponse {
    private String status;
    private int code;
    private String errorMessage;

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}