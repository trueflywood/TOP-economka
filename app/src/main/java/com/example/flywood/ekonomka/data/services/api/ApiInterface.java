package com.example.flywood.ekonomka.data.services.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("api/card/name/{code}/{key}" )
    Call<ProductResponse> getData(@Path("code") String code, @Path("key") String key);
}
