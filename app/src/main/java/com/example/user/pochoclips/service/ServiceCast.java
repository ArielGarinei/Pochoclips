package com.example.user.pochoclips.service;

import com.example.user.pochoclips.models.POJO.ContenedorCast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Arielo on 20/6/2018.
 */

public interface ServiceCast {
    @GET("movie/{movie_id}/credits")
    Call<ContenedorCast> getActor(@Path("movie_id") String movieId,
                                  @Query("api_key") String apiKey);
}
