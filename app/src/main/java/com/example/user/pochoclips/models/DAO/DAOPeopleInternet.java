package com.example.user.pochoclips.models.DAO;

import com.example.user.pochoclips.TMDBHelper;
import com.example.user.pochoclips.interfaces.ResultListener;
import com.example.user.pochoclips.models.POJO.People;
import com.example.user.pochoclips.service.ServicePeople;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aleja on 09/07/2018.
 */

public class DAOPeopleInternet {
    private Retrofit retrofit;
    private ServicePeople servicePeople;

    public DAOPeopleInternet(){
        retrofit = new Retrofit.Builder()
                .baseUrl(TMDBHelper.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        servicePeople = retrofit.create(ServicePeople.class);
    }

    public void ObtenerPersonasDeInternet(final ResultListener<People> escuchadorDelControlador, String personID){
        Call<People> retrofitListener3 = servicePeople.getPeople(personID, TMDBHelper.getApiKey());
        retrofitListener3.enqueue(new Callback<People>() {
            @Override
            public void onResponse(Call<People> call, Response<People> response) {
                People people = response.body();
                escuchadorDelControlador.finish(people);
            }

            @Override
            public void onFailure(Call<People> call, Throwable t) {

            }
        });
    }
}
