package com.example.user.pochoclips.controllers;

import android.content.Context;

import com.example.user.pochoclips.models.POJO.Movie;
import com.example.user.pochoclips.models.database.DataBaseMovie;

import java.util.List;

/**
 * Created by Arielo on 19/7/2018.
 */

public class ControladorMovieRoom {
    private Context context;

    public ControladorMovieRoom(Context context) {
        this.context = context;
    }

    public List<Movie> getMovies(){
        DataBaseMovie dataBaseMovie = DataBaseMovie.getFavDatabaseInstance(context);
        return dataBaseMovie.getAllMovies();
    }
    public List<Movie> getMoviesCategoria(String categoria){
        DataBaseMovie dataBaseMovie = DataBaseMovie.getFavDatabaseInstance(context);
        return dataBaseMovie.getAllMoviesCategoria(categoria);
    }
    public Movie getMovie(Integer id){
        DataBaseMovie dataBaseMovie = DataBaseMovie.getFavDatabaseInstance(context);
        return dataBaseMovie.getMovie(id);
    }

    public void addMovies(List<Movie> movieList){
        DataBaseMovie dataBaseMovie = DataBaseMovie.getFavDatabaseInstance(context);
        dataBaseMovie.insertAll(movieList);
    }

    public void removeMovies(List<Movie> movieList){
        DataBaseMovie dataBaseMovie = DataBaseMovie.getFavDatabaseInstance(context);
        dataBaseMovie.deleteAll(movieList);
    }

    public void addMovie(Movie movie){
        DataBaseMovie dataBaseMovie = DataBaseMovie.getFavDatabaseInstance(context);
        dataBaseMovie.insert(movie);
    }

    public void removeMovie(Movie movie){
        DataBaseMovie dataBaseMovie = DataBaseMovie.getFavDatabaseInstance(context);
        dataBaseMovie.delete(movie);
    }
}
