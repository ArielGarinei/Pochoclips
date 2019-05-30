package com.example.user.pochoclips.controllers;

import android.content.Context;

import com.example.user.pochoclips.HTTPConectionManager;
import com.example.user.pochoclips.interfaces.ResultListener;
import com.example.user.pochoclips.models.DAO.DAOVideoInternet;
import com.example.user.pochoclips.models.POJO.Video;

import java.util.List;

/**
 * Created by aleja on 21/07/2018.
 */

public class ControladorVideos {
    Context context;

    public ControladorVideos(Context context) {
        this.context = context;
    }

    public void ObtenerVideos(final ResultListener<List<Video>> escuchadorDeLaVista, Integer movieId){

        ResultListener<List<Video>> escuchadorDelControlador = new ResultListener<List<Video>>() {

            @Override
            public void finish(List<Video> resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        };

        if (HTTPConectionManager.isNetworkingOnline(context)){
            //LLAMAR AL DAO INTERNET
            DAOVideoInternet daoVideoInternet = new DAOVideoInternet();
            daoVideoInternet.ObtenerVideoDeInternet(escuchadorDelControlador, movieId);
        } else{
            //no internet
        }
    }
}
