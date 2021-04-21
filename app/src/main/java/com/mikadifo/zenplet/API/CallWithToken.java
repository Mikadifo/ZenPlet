package com.mikadifo.zenplet.API;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CallWithToken {
    public static String token = "";
//    public static final String BASE_URL = "https://zenplet.herokuapp.com/";
    //public static final String BASE_URL = "http://192.168.2.102:8080/";
    public static final String BASE_URL = "http://192.168.1.100:8080/";


    public Retrofit getCallToken() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder().addHeader("Authorization", token).build();
                return chain.proceed(newRequest);
            }
        }).build();
        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }

}
