package com.example.user.pochoclips.models.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.SkipQueryVerification;

import com.example.user.pochoclips.models.POJO.Movie;

import java.util.List;

/**
 * Created by aleja on 22/07/2018.
 */
@Dao
public interface DAOFavorito {
    @SkipQueryVerification
    @Query("SELECT * FROM Movie where favorito = 1")
    List<Movie> getAllFavorito();
    @SkipQueryVerification
    @Query("SELECT * FROM Movie where id like :id and favorito = 1")
    Integer getFavorito(Integer id);

    @SkipQueryVerification
    @Query("UPDATE Movie set favorito = 1 WHERE id Like :id")
    Integer insertFavorito(Integer id);

    @SkipQueryVerification
    @Query("UPDATE Movie set favorito = 0 WHERE id Like :id")
    Integer deleteFavorito(Integer id);
}
