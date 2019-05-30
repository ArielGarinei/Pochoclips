package com.example.user.pochoclips.models.database;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.user.pochoclips.models.POJO.People;

import java.util.List;

/**
 * Created by Arielo on 19/7/2018.
 */

public class DataBasePeople {
    private RoomAppDatabase db;

    public DataBasePeople(Context context) {
        this.db = Room.databaseBuilder(context, RoomAppDatabase.class, "database-name").allowMainThreadQueries().build();
    }

    public List<People> getAllPeople(){
        return db.daoPeople().getAllPeople();
    }

    public void insertAll(People[] peopleList){
        db.daoPeople().insertAll(peopleList);
    }

    public void delete(People[] peopleList){
        db.daoPeople().delete(peopleList);
    }
}
