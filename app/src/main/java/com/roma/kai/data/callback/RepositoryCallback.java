package com.roma.kai.data.callback;

public interface RepositoryCallback<T> {
    void onSuccess(T data);

    void onError(String error);
}
