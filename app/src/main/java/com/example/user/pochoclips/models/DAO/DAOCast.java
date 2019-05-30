package com.example.user.pochoclips.models.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.user.pochoclips.models.POJO.Cast;

import java.util.List;

/**
 * Created by Arielo on 19/7/2018.
 */
@Dao
public interface DAOCast {
    @Query("SELECT * FROM Cast")
    List<Cast> getAllPeople();

    @Query("SELECT * FROM Cast WHERE id Like :id")
    Cast getCastWhithId(Integer id);

    @Insert
    void insertAll(List<Cast> movieList);

    @Delete
    void delete(List<Cast> movieList);
}
