package com.example.user.pochoclips.service;

import com.example.user.pochoclips.models.POJO.ContenedorDeImagenes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by User on 24/7/2018.
 */

public interface ServiceImagen {

    @GET("movie/{movie_id}/images")
    Call<ContenedorDeImagenes> getImagenesDePelicula(@Path("movie_id") Integer movieID,
                                                     @Query("api_key") String apiKey);
}
