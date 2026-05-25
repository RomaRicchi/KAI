package com.roma.kai.data.remote.error;

import com.google.gson.Gson;

import retrofit2.Response;

public class ApiErrorParser {
    private static final Gson gson =new Gson();

    public static String parseError(Response<?> response) {
        try {

            if(response.errorBody() == null) {
                return "Error desconocido";
            }

            String errorJson = response.errorBody().string();

            ApiErrorResponse errorResponse = gson.fromJson(errorJson, ApiErrorResponse.class);

            if(errorResponse != null && errorResponse.getErrorMessage() != null) {

                return errorResponse.getErrorMessage();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Error desconocido";
    }
}
