package com.example.user.pochoclips.service;



import com.example.user.pochoclips.models.POJO.ContendorDePeliculas;
import com.example.user.pochoclips.models.POJO.ContenedorDeImagenes;
import com.example.user.pochoclips.models.POJO.ContenedorDeVideos;
import com.example.user.pochoclips.models.POJO.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by DH on 3/4/2018.
 */

public interface ServiceMovie {

    @GET("movie/{movieId}")
    Call<Movie> getMovieDetail(@Path("movieId") Integer movieId,
                               @Query("api_key") String apiKey,
                               @Query("language") String language);

    @GET("genre/list")
    Call<ContendorDePeliculas> getGenreList(@Query("api_key") String apiKey,
                                           @Query("language") String language);

    @GET("movie/popular")
    Call<ContendorDePeliculas> getPopularMovies(@Query("api_key") String apiKey,
                                                @Query("language") String language,
                                                @Query("page") Integer page);

    @GET("movie/upcoming")
    Call<ContendorDePeliculas> getUpcomingMovies(@Query("api_key") String apiKey,
                                                @Query("page") Integer page);

    @GET("movie/top_rated")
    Call<ContendorDePeliculas> getTopRatedMovies(@Query("api_key") String apiKey,
                                                 @Query("page") Integer page);

    @GET("discover/movie")
    Call<ContendorDePeliculas> getPeliFiltradaGenero(@Query("api_key") String apiKey,
                                                     @Query("language") String language,
                                                     @Query("page") Integer page,
                                                     @Query("with_genres") String generoId);

    @GET("movie/{movieId}/videos")
    Call<ContenedorDeVideos> getVideoMovies(@Path("movieId") Integer movieId,
                                            @Query("api_key") String apiKey,
                                            @Query("language") String language);


}
