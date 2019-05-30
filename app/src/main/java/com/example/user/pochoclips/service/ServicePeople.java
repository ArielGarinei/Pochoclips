package com.example.user.pochoclips.service;
import com.example.user.pochoclips.models.POJO.People;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by aleja on 09/07/2018.
 */

public interface ServicePeople {

    @GET("person/{person_id}")
    Call<People> getPeople(@Path("person_id") String movieId,
                           @Query("api_key") String apiKey);
}
