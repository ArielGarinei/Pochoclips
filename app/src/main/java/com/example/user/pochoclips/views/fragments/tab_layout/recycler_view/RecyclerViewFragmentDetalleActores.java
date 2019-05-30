package com.example.user.pochoclips.views.fragments.tab_layout.recycler_view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.pochoclips.R;
import com.example.user.pochoclips.TMDBHelper;
import com.example.user.pochoclips.controllers.ControladorCast;
import com.example.user.pochoclips.interfaces.ResultListener;
import com.example.user.pochoclips.models.POJO.Cast;
import com.example.user.pochoclips.models.POJO.Movie;
import com.example.user.pochoclips.models.POJO.People;
import com.example.user.pochoclips.views.adapter.RecyclerViewAdapterDetalleActores;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerViewFragmentDetalleActores extends Fragment implements RecyclerViewAdapterDetalleActores.EscuchadorDeActores {
    private RecyclerView recyclerView;
    private RecyclerViewAdapterDetalleActores recyclerViewAdapterDetalleActores;
    private NotificadorDeActivitiesActor notificadorDeActivitiesActor;
    private List<Cast> castList;
    private Integer movieid;
    private People people;

    public RecyclerViewFragmentDetalleActores() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notificadorDeActivitiesActor = (NotificadorDeActivitiesActor) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler_view_fragment_detalle_actores, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewActores);
        Bundle bundle = getArguments();
        final Movie pelicula = (Movie) bundle.getSerializable(TMDBHelper.SERIALIZABLE);
        movieid = pelicula.getId();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        cargarActores();

        return view;
    }


    public void cargarActores(){
        ControladorCast controladorCast = new ControladorCast(getContext());
        controladorCast.ObtenerCast(new ResultListener<List<Cast>>() {
            @Override
            public void finish(List<Cast> resultado) {
                castList = resultado;
                recyclerViewAdapterDetalleActores = new RecyclerViewAdapterDetalleActores(castList,RecyclerViewFragmentDetalleActores.this);
                recyclerView.setAdapter(recyclerViewAdapterDetalleActores);
            }
        }, movieid.toString());
    }



    @Override
    public void seleccionaronAlActor(int posicion,List<Cast> castList) {
        notificadorDeActivitiesActor.seleccionaronAlActor(posicion,castList);
    }
    public interface NotificadorDeActivitiesActor{
        public void seleccionaronAlActor (int posicion,List<Cast> castList);
    }
}
