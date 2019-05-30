package com.example.user.pochoclips.models.DAO;

import com.example.user.pochoclips.TMDBHelper;
import com.example.user.pochoclips.interfaces.ResultListener;
import com.example.user.pochoclips.models.POJO.Cast;
import com.example.user.pochoclips.models.POJO.ContenedorCast;
import com.example.user.pochoclips.service.ServiceCast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Arielo on 20/6/2018.
 */

public class DAOCastInternet {
    private Retrofit retrofit;
    private String movieID;
    private ServiceCast serviceCast;

    public DAOCastInternet() {
        retrofit = new Retrofit.Builder()
                .baseUrl(TMDBHelper.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serviceCast = retrofit.create(ServiceCast.class);
    }
    public void ObtenerCastDeInternet(final ResultListener<List<Cast>> escuchadorDelControlador, String movieId){
        Call<ContenedorCast> retrofitListener2 = serviceCast.getActor(movieId,TMDBHelper.getApiKey());
        retrofitListener2.enqueue(new Callback<ContenedorCast>() {
            @Override
            public void onResponse(Call<ContenedorCast> call, Response<ContenedorCast> response) {
                ContenedorCast contenedorCast = response.body();
                escuchadorDelControlador.finish(contenedorCast.getCastList());
            }

            @Override
            public void onFailure(Call<ContenedorCast> call, Throwable t) {
            escuchadorDelControlador.finish(new ArrayList<Cast>());
            }
        });
    }

}
