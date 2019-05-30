package com.example.user.pochoclips.models.DAO;

import com.example.user.pochoclips.TMDBHelper;
import com.example.user.pochoclips.interfaces.ResultListener;
import com.example.user.pochoclips.models.POJO.ContenedorDeVideos;
import com.example.user.pochoclips.models.POJO.Video;
import com.example.user.pochoclips.service.ServiceMovie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aleja on 21/07/2018.
 */

public class DAOVideoInternet {
    private Retrofit retrofit;
    private Integer movieID;
    private ServiceMovie serviceMovie;

    public DAOVideoInternet() {
        retrofit = new Retrofit.Builder()
                .baseUrl(TMDBHelper.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serviceMovie = retrofit.create(ServiceMovie.class);
    }
    public void ObtenerVideoDeInternet(final ResultListener<List<Video>> escuchadorDelControlador, Integer movieId){
        Call<ContenedorDeVideos> retrofitListenerVideo = serviceMovie.getVideoMovies(movieId, TMDBHelper.getApiKey(),TMDBHelper.language_ENGLISH);
        retrofitListenerVideo.enqueue(new Callback<ContenedorDeVideos>() {
            @Override
            public void onResponse(Call<ContenedorDeVideos> call, Response<ContenedorDeVideos> response) {
                ContenedorDeVideos contenedorDeVideos = response.body();
                escuchadorDelControlador.finish(contenedorDeVideos.getVideoList());
            }

            @Override
            public void onFailure(Call<ContenedorDeVideos> call, Throwable t) {

            }

        });
    }
}
