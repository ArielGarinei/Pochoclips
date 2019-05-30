package com.example.user.pochoclips.models.database;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.user.pochoclips.models.POJO.Movie;

import java.util.List;

/**
 * Created by Arielo on 19/7/2018.
 */

public class DataBaseMovie {
    private RoomAppDatabase db;

    private static DataBaseMovie dataBaseMovie;
    public static DataBaseMovie getFavDatabaseInstance(Context context){
        if (dataBaseMovie == null) {
            dataBaseMovie = new DataBaseMovie(context);
        }
        return dataBaseMovie;
    }

    private DataBaseMovie(Context context) {
        this.db = Room.databaseBuilder(context, RoomAppDatabase.class, "database-name").allowMainThreadQueries().build();
    }

    public List<Movie> getAllMovies(){
        return db.daoMovie().getAllMovie();
    }
    public List<Movie> getAllMoviesCategoria(String categoria){
        return db.daoMovie().getAllMovieCategoria(categoria);
    }
    public Movie getMovie(Integer id){
        return db.daoMovie().getRecomendaciontWhithId(id);
    }

    public void insertAll(List<Movie> movieList){
        db.daoMovie().insertAll(movieList);
    }
    public void insert(Movie movie){
        db.daoMovie().insert(movie);
    }

    public void deleteAll(List<Movie> movieList){
        db.daoMovie().deleteAll(movieList);
    }
    public void delete(Movie movie){
        db.daoMovie().delete(movie);
    }

}
