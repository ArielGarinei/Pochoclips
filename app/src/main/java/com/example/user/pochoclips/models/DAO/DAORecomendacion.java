package com.example.user.pochoclips.models.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.user.pochoclips.models.POJO.Recomendacion;

import java.util.List;

/**
 * Created by DH on 18/7/2018.
 */
@Dao
public interface DAORecomendacion {
    @Query("SELECT * FROM Recomendacion")
    List<Recomendacion> getAllRecomendacion();

    @Query("SELECT * FROM Recomendacion WHERE id Like :id")
    Recomendacion getRecomendaciontWhithId(Integer id);

    @Insert
    void insertAll(List<Recomendacion> recomendacionList);

    @Delete
    void delete(List<Recomendacion> recomendacionList);
}
