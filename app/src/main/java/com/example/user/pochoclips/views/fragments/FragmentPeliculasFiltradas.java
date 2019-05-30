package com.example.user.pochoclips.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.pochoclips.R;
import com.example.user.pochoclips.controllers.ControladorMovie;
import com.example.user.pochoclips.interfaces.ResultListener;
import com.example.user.pochoclips.models.POJO.Movie;
import com.example.user.pochoclips.views.adapter.RecyclerViewAdapterPeliculasFiltradas;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPeliculasFiltradas extends Fragment implements RecyclerViewAdapterPeliculasFiltradas.EscuchadorDePeliculaFiltrada {
    public static final String NOMBRE_GENERO = "generoNombre";
    public static final String ID_GENERO = "generoId";
    public static final String IMAGEN_GENERO = "generoImagen";

    private TextView textViewNombreGenero;
    private ImageView imageViewPochoGenero;
    private List<Movie> movieList;
    private GridLayoutManager layoutManager;
    private RecyclerViewAdapterPeliculasFiltradas adapterPeliculasFiltradas;
    private RecyclerView recyclerViewPeliculasFiltradas;
    private NotificadorDePeliculaFiltradaDetalle notificadorDePeliculaFiltradaDetalle;
    private ControladorMovie controller;
    private ProgressBar progressBar;
    private Boolean isLoading = false;
    private String idGenero;

    public FragmentPeliculasFiltradas() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        notificadorDePeliculaFiltradaDetalle = (NotificadorDePeliculaFiltradaDetalle) context;
        movieList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_peliculas_filtradas, container, false);

        textViewNombreGenero = view.findViewById(R.id.nombreDelGeneroElegido_TextView);
        imageViewPochoGenero = view.findViewById(R.id.imagenDelGeneroElegido_ImageView);
        progressBar = view.findViewById(R.id.progressBar_FragmentPeliculasFiltradas);

        recyclerViewPeliculasFiltradas = view.findViewById(R.id.recyclerViewPeliculasFiltradas);
        layoutManager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
        recyclerViewPeliculasFiltradas.setLayoutManager(layoutManager);

        adapterPeliculasFiltradas = new RecyclerViewAdapterPeliculasFiltradas(FragmentPeliculasFiltradas.this);
        recyclerViewPeliculasFiltradas.setAdapter(adapterPeliculasFiltradas);

        Bundle bundle = getArguments();
        String nombreGenero = bundle.getString(NOMBRE_GENERO);
        idGenero = bundle.getString(ID_GENERO);
        Integer imagenGenero = bundle.getInt(IMAGEN_GENERO);

        textViewNombreGenero.setText(nombreGenero);
        imageViewPochoGenero.setImageResource(imagenGenero);

        controller = new ControladorMovie(getContext());
        cargarPaginaDePeliculasFiltradas();

        recyclerViewPeliculasFiltradas.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Log.d("POSICION X",String.valueOf(dx));
                Log.d("POSICION Y",String.valueOf(dy));

                Integer posicionActual = layoutManager.findLastVisibleItemPosition();
                Integer ultimaCelda = layoutManager.getItemCount();

                if(posicionActual == ultimaCelda - 1) {
                    cargarPaginaDePeliculasFiltradas();
                }
            }
        });



        return view;
    }



    @Override
    public void informarPeliculaFiltradaElegida(Integer posicion, List<Movie> listaDePeliculasFiltradas) {
        notificadorDePeliculaFiltradaDetalle.cargarDetalleDePeliculaFiltrada(posicion, listaDePeliculasFiltradas);
    }


    public interface NotificadorDePeliculaFiltradaDetalle {
        public void cargarDetalleDePeliculaFiltrada(Integer posicion, List<Movie> listaPeliculasFiltradas);
    }

    public void cargarPaginaDePeliculasFiltradas() {
        if (isLoading) {
            return;
        }
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        isLoading = true;

        controller.ObtenerPeliculasFiltradas(new ResultListener<List<Movie>>() {
            @Override
            public void finish(List<Movie> resultado) {
                adapterPeliculasFiltradas.agregarAListaDePeliculasFiltradas(resultado);
                progressBar.setIndeterminate(false);
                progressBar.setVisibility(View.INVISIBLE);
                isLoading = false;
            }
        },idGenero);

    }


}
