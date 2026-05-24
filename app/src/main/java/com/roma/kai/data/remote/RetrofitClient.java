package com.roma.kai.data.remote;

import android.content.Context;

import com.roma.kai.session.SessionManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://10.0.2.2:8080/";
    private static Retrofit retrofit = null;

    public static ApiService getService(Context context) {
        if(retrofit == null) {
            SessionManager sessionManager = SessionManager.getInstance(context.getApplicationContext());
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new AuthInterceptor(sessionManager)).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit.create(ApiService.class);
    }
}
