package com.ecom.client;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EcommServices {

    @GET("json")
    Call<JsonElement> getProductJson();


}
