package com.example.destek.network;

import com.example.destek.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiFeedsInterface {

    @GET("Shop/GetByAreaId/{AreaId}")
    Call<BaseResponse> getArea(@Path("AreaId") int mAreaId);


    @GET("api/jsonBlob/7a640a9d-443a-11ea-9fc2-d347e19004ba")
    Call<BaseResponse> getCategories();

   }