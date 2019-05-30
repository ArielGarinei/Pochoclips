package com.example.user.pochoclips.controllers;

import android.content.Context;

import com.example.user.pochoclips.models.POJO.Movie;
import com.example.user.pochoclips.models.database.DataBaseFavoritos;

import java.util.List;

/**
 * Created by aleja on 22/07/2018.
 */

public class ControladorFavoritosRoom {
    Context context;

    public ControladorFavoritosRoom(Context context) {
        this.context = context;
    }

    public List<Movie> getFavoritos(){
        DataBaseFavoritos dataBaseFavoritos = DataBaseFavoritos.getFavDatabaseInstance(context);
        return dataBaseFavoritos.getAllFavoritos();
    }
    public Integer getFavorito(Integer movieId){
        DataBaseFavoritos dataBaseFavoritos = DataBaseFavoritos.getFavDatabaseInstance(context);
        return dataBaseFavoritos.getFavorito(movieId);
    }

    public void addFavoritos(Integer movieId){
        DataBaseFavoritos dataBaseFavoritos = DataBaseFavoritos.getFavDatabaseInstance(context);
        dataBaseFavoritos.insert(movieId);
    }

    public void removeFavoritos(Integer movieId){
        DataBaseFavoritos dataBaseFavoritos = DataBaseFavoritos.getFavDatabaseInstance(context);
        dataBaseFavoritos.delete(movieId);
    }
}
