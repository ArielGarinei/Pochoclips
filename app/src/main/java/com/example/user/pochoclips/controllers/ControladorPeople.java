package com.example.user.pochoclips.controllers;

import android.content.Context;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.user.pochoclips.HTTPConectionManager;
import com.example.user.pochoclips.interfaces.ResultListener;
import com.example.user.pochoclips.models.DAO.DAOPeople;
import com.example.user.pochoclips.models.DAO.DAOPeopleInternet;
import com.example.user.pochoclips.models.POJO.People;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aleja on 09/07/2018.
 */

public class ControladorPeople {
    private Context context;
    private List<People> peopleList;
    public ControladorPeople(Context context) {
        this.context = context;
        this.peopleList = new ArrayList<>();
    }

    public void ObtenerPersonas(final ResultListener<People> escuchadorDeLaVista, String movieId) {

        if (HTTPConectionManager.isNetworkingOnline(context)) {
            ResultListener<People> escuchadorDelControlador = new ResultListener<People>() {
                @Override
                public void finish(People resultado) {
                    ControladorPeopleRoom controladorPeopleRoom = new ControladorPeopleRoom(context);
                    controladorPeopleRoom.removePeople(resultado);
                    controladorPeopleRoom.addPeople(resultado);
                    escuchadorDeLaVista.finish(resultado);
                }
            };

            DAOPeopleInternet daoPeopleInternet = new DAOPeopleInternet();
            daoPeopleInternet.ObtenerPersonasDeInternet(escuchadorDelControlador,movieId);
        } else{
            ControladorPeopleRoom controladorPeopleRoom = new ControladorPeopleRoom(context);
            for (People mPeople : controladorPeopleRoom.getPeople()) {
                        if (mPeople.getId().equals(movieId)){
                            escuchadorDeLaVista.finish(mPeople);
                        }
            }

        }
    }
}