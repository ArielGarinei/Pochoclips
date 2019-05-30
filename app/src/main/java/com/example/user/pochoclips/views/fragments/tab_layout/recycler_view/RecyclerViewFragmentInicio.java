package com.example.user.pochoclips.views.fragments.tab_layout.recycler_view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.example.user.pochoclips.R;
import com.example.user.pochoclips.controllers.ControladorMovie;
import com.example.user.pochoclips.interfaces.ResultListener;
import com.example.user.pochoclips.models.POJO.Movie;
import com.example.user.pochoclips.views.adapter.RecyclerViewAdapterInicio;

import java.util.List;

public class RecyclerViewFragmentInicio extends Fragment implements RecyclerViewAdapterInicio.EscuchadorDePeliculas {

    private NotificadorDeActivities notificadorDeActivities;

    private RecyclerViewAdapterInicio adapterRvUltimosLanzamientos;
    private RecyclerViewAdapterInicio adapterRvPopulares;
    private RecyclerViewAdapterInicio adapterRvTopRated;

    private RecyclerView recyclerViewUltimosLanzamientos;
    private RecyclerView recyclerViewPopulares;
    private RecyclerView recyclerViewTopRated;


    public RecyclerViewFragmentInicio() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notificadorDeActivities = (NotificadorDeActivities) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recycler_view_fragment_inicio, container, false);

        recyclerViewUltimosLanzamientos = view.findViewById(R.id.recyclerViewUltimosLanzamientos);
        CarouselLayoutManager layoutManagerUltimosLanzamientos = new CarouselLayoutManager(LinearLayoutManager.HORIZONTAL, true);
        layoutManagerUltimosLanzamientos.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerViewUltimosLanzamientos.setLayoutManager(layoutManagerUltimosLanzamientos);
        recyclerViewUltimosLanzamientos.setHasFixedSize(false);

        recyclerViewPopulares = view.findViewById(R.id.recyclerViewPopulares);
        CarouselLayoutManager layoutManagerPopulares = new CarouselLayoutManager(LinearLayoutManager.HORIZONTAL, true);
        layoutManagerPopulares.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerViewPopulares.setLayoutManager(layoutManagerPopulares);
        recyclerViewPopulares.setHasFixedSize(false);

        recyclerViewTopRated = view.findViewById(R.id.recyclerViewTopRated);
        CarouselLayoutManager layoutManagerTopRated = new CarouselLayoutManager(LinearLayoutManager.HORIZONTAL, true);
        layoutManagerTopRated.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerViewTopRated.setLayoutManager(layoutManagerTopRated);
        recyclerViewTopRated.setHasFixedSize(false);

        cargarPeliculasUpcoming();
        cargarPeliculasPopulares();
        cargarPeliculasTopRated();

        return view;
    }

    public void cargarPeliculasUpcoming(){
        ControladorMovie controller = new ControladorMovie(getContext());
        controller.ObtenerPeliculasUpcoming(new ResultListener<List<Movie>>() {
            @Override
            public void finish(List<Movie> resultado) {
                List<Movie> movieList = resultado;
                adapterRvUltimosLanzamientos = new RecyclerViewAdapterInicio(movieList, RecyclerViewFragmentInicio.this);
                recyclerViewUltimosLanzamientos.setAdapter(adapterRvUltimosLanzamientos);
            }
        });
    }

    public void cargarPeliculasPopulares(){
        ControladorMovie controller = new ControladorMovie(getContext());
        controller.ObtenerPeliculasPopulares(new ResultListener<List<Movie>>() {
            @Override
            public void finish(List<Movie> resultado) {
                List<Movie> movieList = resultado;
                adapterRvPopulares = new RecyclerViewAdapterInicio(movieList, RecyclerViewFragmentInicio.this);
                recyclerViewPopulares.setAdapter(adapterRvPopulares);
            }
        });
    }

    public void cargarPeliculasTopRated(){
        ControladorMovie controller = new ControladorMovie(getContext());
        controller.ObtenerPeliculasTopRated(new ResultListener<List<Movie>>() {
            @Override
            public void finish(List<Movie> resultado) {
                List<Movie> movieList = resultado;
                adapterRvTopRated = new RecyclerViewAdapterInicio(movieList, RecyclerViewFragmentInicio.this);
                recyclerViewTopRated.setAdapter(adapterRvTopRated);
            }
        });
    }
    
    @Override
    public void seleccionaronLaPelicula(int posicion,List<Movie> movieList) {
        notificadorDeActivities.seleccionaronLaPelicula(posicion, movieList);
    }

    public interface NotificadorDeActivities {
        void seleccionaronLaPelicula(int posicion, List<Movie> movieList);
    }

}
