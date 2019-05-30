package com.example.user.pochoclips.models.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.user.pochoclips.models.DAO.DAOCast;
import com.example.user.pochoclips.models.DAO.DAOFavorito;
import com.example.user.pochoclips.models.DAO.DAOMovie;
import com.example.user.pochoclips.models.DAO.DAOPeople;
import com.example.user.pochoclips.models.DAO.DAORecomendacion;
import com.example.user.pochoclips.models.POJO.Cast;
import com.example.user.pochoclips.models.POJO.Movie;
import com.example.user.pochoclips.models.POJO.People;
import com.example.user.pochoclips.models.POJO.Recomendacion;

/**
 * Created by DH on 18/7/2018.
 */
@Database(entities = {Recomendacion.class, Movie.class, People.class, Cast.class}, version = 1,exportSchema = false)
public abstract class RoomAppDatabase extends RoomDatabase {
    public abstract DAOMovie daoMovie();
    public abstract DAOPeople daoPeople();
    public abstract DAOFavorito daoFavorito();
    public abstract DAORecomendacion daoRecomendacion();
    public abstract DAOCast daoCast();

}
