package com.example.flywood.ekonomka.data.services.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    public final static String BASE_URL = "https://barcodes.olegon.ru/";
    public final static String APIKEY = "B393120722231310299937802713723";

    private static Retrofit retrofit = null;

    public static Retrofit getProductName() {
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
