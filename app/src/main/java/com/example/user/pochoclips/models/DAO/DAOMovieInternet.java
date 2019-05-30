package com.example.user.pochoclips.models.DAO;

import android.util.Log;
import android.widget.Toast;

import com.example.user.pochoclips.service.ServiceMovie;
import com.example.user.pochoclips.TMDBHelper;
import com.example.user.pochoclips.interfaces.ResultListener;
import com.example.user.pochoclips.models.POJO.ContendorDePeliculas;
import com.example.user.pochoclips.models.POJO.Movie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DAOMovieInternet {

    private Retrofit retrofit;
    private ServiceMovie serviceMovie;


    public DAOMovieInternet() {
        retrofit = new Retrofit.Builder()
                .baseUrl(TMDBHelper.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serviceMovie = retrofit.create(ServiceMovie.class);
    }
    public void ObtenerMoviewDetail(Integer movieID, final ResultListener<Movie> escuchadorDelControlador) {

        Call<Movie> retrofitListener = serviceMovie.getMovieDetail(movieID,TMDBHelper.getApiKey(),TMDBHelper.language_ENGLISH);
        retrofitListener.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movie = response.body();
                escuchadorDelControlador.finish(movie);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
            }
        });
    }

    public void ObtenerPeliculasPopularesInternet(final ResultListener<List<Movie>> escuchadorDelControlador) {

        Call<ContendorDePeliculas> retrofitListener = serviceMovie.getPopularMovies(TMDBHelper.getApiKey(),TMDBHelper.language_ENGLISH,1);
        retrofitListener.enqueue(new Callback<ContendorDePeliculas>() {
            @Override
            public void onResponse(Call<ContendorDePeliculas> call, Response<ContendorDePeliculas> response) {

                ContendorDePeliculas contendorDePeliculas = response.body();

                for (Movie movie : contendorDePeliculas.getListaDeMovies()) {
                    movie.setCategoria("popular");
                }

                escuchadorDelControlador.finish(contendorDePeliculas.getListaDeMovies());
            }

            @Override
            public void onFailure(Call<ContendorDePeliculas> call, Throwable t) {

                escuchadorDelControlador.finish(new ArrayList<Movie>());
            }
        });
    }

    public void ObtenerPeliculasUpcomingDeInternet(final ResultListener<List<Movie>> escuchadorDelControlador){

        Call<ContendorDePeliculas> retrofirListener = serviceMovie.getUpcomingMovies(TMDBHelper.getApiKey(),1);
        retrofirListener.enqueue(new Callback<ContendorDePeliculas>() {
            @Override
            public void onResponse(Call<ContendorDePeliculas> call, Response<ContendorDePeliculas> response) {
                ContendorDePeliculas contendorDePeliculas = response.body();
                for (Movie movie : contendorDePeliculas.getListaDeMovies()) {
                    movie.setCategoria("upComing");
                }
                escuchadorDelControlador.finish(contendorDePeliculas.getListaDeMovies());
            }

            @Override
            public void onFailure(Call<ContendorDePeliculas> call, Throwable t) {

            }
        });
    }

    public void ObtenerPeliculasTopRatedInternet(final ResultListener<List<Movie>> escuchadorDelControlador){

        Call<ContendorDePeliculas> retrofirListener = serviceMovie.getTopRatedMovies(TMDBHelper.getApiKey(),1);
        retrofirListener.enqueue(new Callback<ContendorDePeliculas>() {
            @Override
            public void onResponse(Call<ContendorDePeliculas> call, Response<ContendorDePeliculas> response) {
                ContendorDePeliculas contendorDePeliculas = response.body();
                for (Movie movie : contendorDePeliculas.getListaDeMovies()) {
                    movie.setCategoria("topRated");
                }
                escuchadorDelControlador.finish(contendorDePeliculas.getListaDeMovies());
            }

            @Override
            public void onFailure(Call<ContendorDePeliculas> call, Throwable t) {

            }
        });
    }

    public void ObtenerPeliculasFiltradasInternet(final ResultListener<List<Movie>> escuchadorDelControlador, String generoId, Integer page){

        Call<ContendorDePeliculas> retrofirListener = serviceMovie.getPeliFiltradaGenero(TMDBHelper.getApiKey(),TMDBHelper.language_ENGLISH, page,generoId);
        retrofirListener.enqueue(new Callback<ContendorDePeliculas>() {
            @Override
            public void onResponse(Call<ContendorDePeliculas> call, Response<ContendorDePeliculas> response) {
                ContendorDePeliculas contendorDePeliculas = response.body();
                escuchadorDelControlador.finish(contendorDePeliculas.getListaDeMovies());
            }

            @Override
            public void onFailure(Call<ContendorDePeliculas> call, Throwable t) {

            }
        });
    }

}

