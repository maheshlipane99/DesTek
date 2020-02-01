package com.example.destek.Application;

import android.app.Application;
import android.content.Context;

import com.example.destek.network.ConnectivityReceiver;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class MyApplication extends Application {

    static Context mContext;
    private static MyApplication mInstance;
    private static boolean mRequestingLocationUpdates;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        mContext = getApplicationContext();


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        //logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        // logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);


    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    public static Context getContext() {
        return mContext;
    }

    public static boolean getIsRequestingLocationUpdates() {
        return mRequestingLocationUpdates;
    }

    public static void setRequestingLocationUpdates(boolean mRequestingLocationUpdates) {
        MyApplication.mRequestingLocationUpdates = mRequestingLocationUpdates;
    }
}