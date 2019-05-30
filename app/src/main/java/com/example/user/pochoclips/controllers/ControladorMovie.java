package com.example.user.pochoclips.controllers;

import android.content.Context;

import com.example.user.pochoclips.HTTPConectionManager;
import com.example.user.pochoclips.models.DAO.DAOMovieInternet;
import com.example.user.pochoclips.models.POJO.Movie;
import com.example.user.pochoclips.interfaces.ResultListener;

import java.util.List;

public class ControladorMovie {

    private Context context;
    private Integer page = 1;

    public ControladorMovie(Context context) {
        this.context = context;
    }

    public void ObtenerMoviewDetail(final ResultListener<Movie> escuchadorDeLaVista, Integer id){
        if (HTTPConectionManager.isNetworkingOnline(context)){
            ResultListener<Movie> escuchadorDelControlador = new ResultListener<Movie>() {
                @Override
                public void finish(Movie resultado) {
                ControladorMovieRoom controladorMovieRoom = new ControladorMovieRoom(context);
                controladorMovieRoom.addMovie(resultado);
                escuchadorDeLaVista.finish(resultado);
                }
            };
            DAOMovieInternet daoMovieInternet = new DAOMovieInternet();
            daoMovieInternet.ObtenerMoviewDetail(id, escuchadorDelControlador);
        }else {
            ControladorMovieRoom controladorMovieRoom = new ControladorMovieRoom(context);
            escuchadorDeLaVista.finish(controladorMovieRoom.getMovie(id));
        }
    }



    public void ObtenerPeliculasPopulares(final ResultListener<List<Movie>> escuchadorDeLaVista){


        if (HTTPConectionManager.isNetworkingOnline(context)){
            ResultListener<List<Movie>> escuchadorDelControlador = new ResultListener<List<Movie>>() {
                @Override
                public void finish(List<Movie> resultado) {
                    ControladorMovieRoom controladorMovieRoom = new ControladorMovieRoom(context);
                    //controladorMovieRoom.removeMovies(resultado);
                    controladorMovieRoom.addMovies(resultado);
                    escuchadorDeLaVista.finish(resultado);
                }
            };
            DAOMovieInternet daoMovieInternet = new DAOMovieInternet();
            daoMovieInternet.ObtenerPeliculasPopularesInternet(escuchadorDelControlador);
        } else{
            ControladorMovieRoom controladorMovieRoom = new ControladorMovieRoom(context);
            escuchadorDeLaVista.finish(controladorMovieRoom.getMoviesCategoria("popular"));
        }
    }

    public void ObtenerPeliculasUpcoming(final ResultListener<List<Movie>> escuchadorDeLaVista){

        if (HTTPConectionManager.isNetworkingOnline(context)){
            ResultListener<List<Movie>> escuchadorDelControlador = new ResultListener<List<Movie>>() {
                @Override
                public void finish(List<Movie> resultado) {
                    ControladorMovieRoom controladorMovieRoom = new ControladorMovieRoom(context);
                    //controladorMovieRoom.removeMovies(resultado);
                    controladorMovieRoom.addMovies(resultado);
                    escuchadorDeLaVista.finish(resultado);
                }
            };
            DAOMovieInternet daoMovieInternet = new DAOMovieInternet();
            daoMovieInternet.ObtenerPeliculasUpcomingDeInternet(escuchadorDelControlador);
        } else{
            ControladorMovieRoom controladorMovieRoom = new ControladorMovieRoom(context);
            escuchadorDeLaVista.finish(controladorMovieRoom.getMoviesCategoria("upComing"));
        }
    }

    public void ObtenerPeliculasTopRated(final ResultListener<List<Movie>> escuchadorDeLaVista){

        if (HTTPConectionManager.isNetworkingOnline(context)){
            ResultListener<List<Movie>> escuchadorDelControlador = new ResultListener<List<Movie>>() {
                @Override
                public void finish(List<Movie> resultado) {
                    ControladorMovieRoom controladorMovieRoom = new ControladorMovieRoom(context);
                   // controladorMovieRoom.removeMovies(resultado);
                    controladorMovieRoom.addMovies(resultado);
                    escuchadorDeLaVista.finish(resultado);
                }
            };
            DAOMovieInternet daoMovieInternet = new DAOMovieInternet();
            daoMovieInternet.ObtenerPeliculasTopRatedInternet(escuchadorDelControlador);
        } else{
            ControladorMovieRoom controladorMovieRoom = new ControladorMovieRoom(context);
            escuchadorDeLaVista.finish(controladorMovieRoom.getMoviesCategoria("topRated"));
        }
    }

    public void ObtenerPeliculasFiltradas(final ResultListener<List<Movie>> escuchadorDeLaVista,String generoId){

        if (HTTPConectionManager.isNetworkingOnline(context)){
            ResultListener<List<Movie>> escuchadorDelControlador = new ResultListener<List<Movie>>() {
                @Override
                public void finish(List<Movie> resultado) {
                    /*ControladorMovieRoom controladorMovieRoom = new ControladorMovieRoom(context);
                    controladorMovieRoom.removeMovie(resultado);
                    controladorMovieRoom.addMovie(resultado);*/

                    page++;
                    escuchadorDeLaVista.finish(resultado);
                }
            };
            DAOMovieInternet daoMovieInternet = new DAOMovieInternet();
            daoMovieInternet.ObtenerPeliculasFiltradasInternet(escuchadorDelControlador, generoId, page);
        } else{
            /*ControladorMovieRoom controladorMovieRoom = new ControladorMovieRoom(context);
            escuchadorDeLaVista.finish(controladorMovieRoom.getMovie());*/
            //TODO: Revisar esto
        }
    }

}
