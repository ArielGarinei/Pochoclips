package com.example.user.pochoclips.models.database;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.user.pochoclips.models.POJO.Movie;

import java.util.List;

/**
 * Created by aleja on 22/07/2018.
 */

public class DataBaseFavoritos {
    private RoomAppDatabase db;

    private static DataBaseFavoritos dataBaseFavoritos;
    public static DataBaseFavoritos getFavDatabaseInstance(Context context){
        if (dataBaseFavoritos == null) {
            dataBaseFavoritos = new DataBaseFavoritos(context);
        }
        return dataBaseFavoritos;
    }

    private DataBaseFavoritos(Context context){
        db = Room.databaseBuilder(context, RoomAppDatabase.class, "database-name").allowMainThreadQueries().build();
    }

    public List<Movie> getAllFavoritos(){
        return  db.daoFavorito().getAllFavorito();
    }

    public Integer getFavorito(Integer id){
        return  db.daoFavorito().getFavorito(id);
    }

    public void insert (Integer idDePeliculaParaFavorito){
        db.daoFavorito().insertFavorito(idDePeliculaParaFavorito);
    }

    public void delete (Integer idDePeliculaParaFavorito){
        db.daoFavorito().deleteFavorito(idDePeliculaParaFavorito);
    }
}
