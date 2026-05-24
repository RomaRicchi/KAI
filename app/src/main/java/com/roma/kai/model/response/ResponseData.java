package com.roma.kai.model.response;

public class ResponseData<T> {
    private String status;
    private int code;
    private T data;
    private String errorMessage;

    public ResponseData(String status, int code, T data, String errorMessage) {
        this.status = status;
        this.code = code;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public ResponseData() {}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
