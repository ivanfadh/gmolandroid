package com.example.gmol_android.service;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 3/25/2018.
 */

public class BaseRestService {
    public static Retrofit retrofit;
    public static final String URL = "http://140.82.42.144/gmol/public/";

    final static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build();


    public static Retrofit initializeRetrofit(){
     if(retrofit==null){
         retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create())
                 .client(okHttpClient)
                 .build();
     }
     return retrofit;
    }
}
