package com.ecom;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.ecom.client.EcommServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EcommApp extends Application {

    private static EcommServices ecommServices;

    @Override
    public void onCreate() {
        super.onCreate();
        setupRestClient();
    }


    /**
     * Set up rest client
     */
    private void setupRestClient() {


        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        OkHttpClient.Builder httpClient;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient = new OkHttpClient.Builder();
        httpClient.interceptors().add(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.REST_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();


        ecommServices = retrofit.create(EcommServices.class);

    }

    public static EcommServices get() {
        return ecommServices;
    }
}
