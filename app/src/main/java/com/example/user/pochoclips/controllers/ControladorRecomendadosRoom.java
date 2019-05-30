package com.example.user.pochoclips.controllers;

import android.content.Context;

import com.example.user.pochoclips.models.POJO.Recomendacion;
import com.example.user.pochoclips.models.database.DataBaseRecomendaciones;

import java.util.List;

/**
 * Created by DH on 18/7/2018.
 */

public class ControladorRecomendadosRoom {
    private Context context;

    public ControladorRecomendadosRoom(Context context) {
        this.context = context;
    }

    public List<Recomendacion> getRecomendacion(){
        DataBaseRecomendaciones dataBaseRecomendaciones = new DataBaseRecomendaciones(context);
        return dataBaseRecomendaciones.getAllRecomendacion();
    }

    public void addRecomendacion(List<Recomendacion> mRecomendacionList){
        DataBaseRecomendaciones dataBaseRecomendaciones = new DataBaseRecomendaciones(context);
        dataBaseRecomendaciones.insertAll(mRecomendacionList);
    }

    public void removeRecomendacion(List<Recomendacion> mRecomendacionList){
        DataBaseRecomendaciones dataBaseRecomendaciones = new DataBaseRecomendaciones(context);
        dataBaseRecomendaciones.delete(mRecomendacionList);
    }
}
