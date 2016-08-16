package com.example.root.roadamic.ApiImplementation;

import com.example.root.roadamic.Models.ApiResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by root on 15/8/16.
 */
public interface ApiInterface {

    @GET("maps/api/place/nearbysearch/json?key=AIzaSyB0RTmWama-0b2YQivqHhQh0HTYb0s2GgM")
    Call<ApiResult>getApiResults(@Query("location") String location,@Query("distance") int distance,@Query("keyword") String keyword, @Query("key") String key);
}
