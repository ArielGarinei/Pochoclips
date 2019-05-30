package com.example.user.pochoclips.controllers;

import android.content.Context;

import com.example.user.pochoclips.HTTPConectionManager;
import com.example.user.pochoclips.interfaces.ResultListener;
import com.example.user.pochoclips.models.DAO.DAOImagenInternet;
import com.example.user.pochoclips.models.DAO.DAOVideoInternet;
import com.example.user.pochoclips.models.POJO.Imagen;
import com.example.user.pochoclips.models.POJO.Video;

import java.util.List;

/**
 * Created by User on 24/7/2018.
 */

public class ControladorImagen {

    Context context;

    public ControladorImagen(Context context) {
        this.context = context;
    }

    public void obtenerImagenesDePelicula(final ResultListener<List<Imagen>> escuchadorDeLaVista, Integer movieID) {

        ResultListener<List<Imagen>> escuchadorDelControlador = new ResultListener<List<Imagen>>() {
            @Override
            public void finish(List<Imagen> resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        };

        if (HTTPConectionManager.isNetworkingOnline(context)) {
            DAOImagenInternet daoImagenInternet = new DAOImagenInternet();
            daoImagenInternet.obtenerImagenesDePelicula(escuchadorDelControlador, movieID);
        } else {
            return;
        }


    }
}
