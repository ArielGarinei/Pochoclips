package com.example.user.pochoclips.models.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.user.pochoclips.models.POJO.People;

import java.util.List;

/**
 * Created by DH on 18/7/2018.
 */
@Dao
public interface DAOPeople {
    @Query("SELECT * FROM People")
    List<People> getAllPeople();

    @Query("SELECT * FROM People WHERE imdb_id Like :imdb_id")
    People getRecomendaciontWhithId(Integer imdb_id);

    @Insert
    void insertAll(People[] peopleList);

    @Delete
    void delete(People[] peopleList);
}
