package com.example.user.pochoclips.models.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.user.pochoclips.models.POJO.Movie;

import java.util.List;

/**
 * Created by DH on 18/7/2018.
 */
@Dao
public interface DAOMovie {
    @Query("SELECT * FROM Movie")
    List<Movie> getAllMovie();

    @Query("SELECT * FROM Movie WHERE categoria =  :categoria")
    List<Movie> getAllMovieCategoria(String categoria);

    @Query("SELECT * FROM Movie WHERE id Like :id")
    Movie getRecomendaciontWhithId(Integer id);

    @Query("DELETE FROM Movie WHERE id Like :id AND favorito = 0")
    void deleteNoFav(Integer id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Movie> movieList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie movie);

    @Delete
    void deleteAll(List<Movie> movieList);

    @Delete
    void delete(Movie movie);
}
