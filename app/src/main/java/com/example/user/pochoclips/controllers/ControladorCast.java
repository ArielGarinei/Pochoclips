package com.example.user.pochoclips.controllers;

import android.content.Context;

import com.example.user.pochoclips.HTTPConectionManager;
import com.example.user.pochoclips.interfaces.ResultListener;
import com.example.user.pochoclips.models.DAO.DAOCastInternet;
import com.example.user.pochoclips.models.POJO.Cast;

import java.util.List;

/**
 * Created by Arielo on 20/6/2018.
 */

public class ControladorCast {
    private Context context;

    public ControladorCast(Context context) {
        this.context = context;
    }

    public void ObtenerCast(final ResultListener<List<Cast>> escuchadorDeLaVista, String movieId){

        ResultListener<List<Cast>> escuchadorDelControlador = new ResultListener<List<Cast>>() {
            @Override
            public void finish(List<Cast> resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        };

        if (HTTPConectionManager.isNetworkingOnline(context)){
            //LLAMAR AL DAO INTERNET
            DAOCastInternet daoCastInternet = new DAOCastInternet();
            daoCastInternet.ObtenerCastDeInternet(escuchadorDelControlador,movieId);
        } else{
        }
    }

}
