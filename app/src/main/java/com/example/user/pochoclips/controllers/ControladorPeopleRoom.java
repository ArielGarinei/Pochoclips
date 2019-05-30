package com.example.user.pochoclips.controllers;

import android.content.Context;

import com.example.user.pochoclips.models.POJO.People;
import com.example.user.pochoclips.models.POJO.Recomendacion;
import com.example.user.pochoclips.models.database.DataBasePeople;
import com.example.user.pochoclips.models.database.DataBaseRecomendaciones;

import java.util.List;

/**
 * Created by Arielo on 19/7/2018.
 */

public class ControladorPeopleRoom {
    private Context context;

    public ControladorPeopleRoom(Context context) {
        this.context = context;
    }

    public List<People> getPeople(){
        DataBasePeople dataBasePeople = new DataBasePeople(context);
        return dataBasePeople.getAllPeople();
    }

    public void addPeople(People... people){
        DataBasePeople dataBasePeople = new DataBasePeople(context);
        dataBasePeople.insertAll(people);
    }

    public void removePeople(People... people){
        DataBasePeople dataBasePeople = new DataBasePeople(context);
        dataBasePeople.delete(people);
    }
}
