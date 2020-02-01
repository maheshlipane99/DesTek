package com.example.destek.network;

import android.text.TextUtils;
import android.util.Log;

import com.example.destek.Application.MyApplication;
import com.example.destek.Utils.DateDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Date;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    public static final String BASE_URL = "https://jsonblob.com/";

    private static final String TAG = RestClient.class.getSimpleName();

    private static Gson gson = new GsonBuilder()
            //  .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
            .registerTypeAdapter(Date.class, new DateDeserializer())
            .setLenient()
            .create();


    public static final OkHttpClient.Builder getHttpClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        //  logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        String token = null;


        if (!TextUtils.isEmpty(token)) {
            Log.i(TAG, "getHttpClient: Token " + token);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().
                            build();
                    return chain.proceed(request);
                }
            });
        }

        return httpClient;
    }


    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    private static ApiFeedsInterface apiService = retrofit.create(ApiFeedsInterface.class);

    public static ApiFeedsInterface getApiService() {
        ReInstantiate();
        return retrofit.create(ApiFeedsInterface.class);
    }

    public static void ReInstantiate() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(ApiFeedsInterface.class);
    }

}
