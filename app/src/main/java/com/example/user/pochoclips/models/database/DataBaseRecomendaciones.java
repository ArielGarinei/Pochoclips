package com.example.user.pochoclips.models.database;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.user.pochoclips.models.POJO.Recomendacion;

import java.util.List;

/**
 * Created by DH on 18/7/2018.
 */

public class DataBaseRecomendaciones {
    private RoomAppDatabase db;

    public DataBaseRecomendaciones(Context context) {
        this.db = Room.databaseBuilder(context, RoomAppDatabase.class, "database-name").allowMainThreadQueries().build();
    }

    public List<Recomendacion> getAllRecomendacion(){
        return db.daoRecomendacion().getAllRecomendacion();
    }

    public void insertAll(List<Recomendacion> recomendacionList){
        db.daoRecomendacion().insertAll(recomendacionList);
    }

    public void delete(List<Recomendacion> recomendacionList){
        db.daoRecomendacion().delete(recomendacionList);
    }
}
