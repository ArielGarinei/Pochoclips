package com.example.user.pochoclips.models.DAO;

import com.example.user.pochoclips.TMDBHelper;
import com.example.user.pochoclips.interfaces.ResultListener;
import com.example.user.pochoclips.models.POJO.ContendorDePeliculas;
import com.example.user.pochoclips.models.POJO.ContenedorDeImagenes;
import com.example.user.pochoclips.models.POJO.Imagen;
import com.example.user.pochoclips.models.POJO.Movie;
import com.example.user.pochoclips.service.ServiceImagen;
import com.example.user.pochoclips.service.ServiceMovie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 24/7/2018.
 */

public class DAOImagenInternet {

    private Retrofit retrofit;
    private ServiceImagen serviceImagen;

    public DAOImagenInternet() {
        retrofit = new Retrofit.Builder()
                .baseUrl(TMDBHelper.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serviceImagen = retrofit.create(ServiceImagen.class);
    }

    public void obtenerImagenesDePelicula(final ResultListener<List<Imagen>> escuchadorDelControlador, Integer movieID){

        Call<ContenedorDeImagenes> retrofitListener = serviceImagen.getImagenesDePelicula(movieID, TMDBHelper.getApiKey());
        retrofitListener.enqueue(new Callback<ContenedorDeImagenes>() {
            @Override
            public void onResponse(Call<ContenedorDeImagenes> call, Response<ContenedorDeImagenes> response) {
                ContenedorDeImagenes contenedorDeImagenes = response.body();
                escuchadorDelControlador.finish(contenedorDeImagenes.getListaDeImagenes());
            }

            @Override
            public void onFailure(Call<ContenedorDeImagenes> call, Throwable t) {

            }
        });
    }

}
