package com.example.user.pochoclips.models.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DH on 13/6/2018.
 */

public class ContendorDePeliculas {
    @SerializedName("results")
    private List<Movie> listaDeMovies;

    public ContendorDePeliculas(List<Movie> listaDeMovies) {
        this.listaDeMovies = listaDeMovies;
    }

    public List<Movie> getListaDeMovies() {
        return listaDeMovies;
    }
}
