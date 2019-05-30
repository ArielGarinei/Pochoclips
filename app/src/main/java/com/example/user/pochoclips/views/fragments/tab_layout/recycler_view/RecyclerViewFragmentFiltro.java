package com.example.user.pochoclips.views.fragments.tab_layout.recycler_view;

import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.pochoclips.R;
import com.example.user.pochoclips.models.POJO.Filtro;
import com.example.user.pochoclips.views.adapter.RecyclerViewAdapterFiltro;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewFragmentFiltro extends DialogFragment implements RecyclerViewAdapterFiltro.EscuchadorDeFiltro {

    private RecyclerView recyclerView;
    private List<Filtro> listaDeFiltros = new ArrayList<>();
    private NotificadorDeFiltroEnActivity notificadorDeFiltroEnActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        notificadorDeFiltroEnActivity = (NotificadorDeFiltroEnActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler_view_filtro, container, false);

        listaDeFiltros = cargarListaDeFiltros();

        recyclerView = view.findViewById(R.id.filtros_RecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),3,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        RecyclerViewAdapterFiltro recyclerViewAdapterFiltro = new RecyclerViewAdapterFiltro(listaDeFiltros,RecyclerViewFragmentFiltro.this);
        recyclerView.setAdapter(recyclerViewAdapterFiltro);

        this.getDialog().setTitle("FILTER MOVIE BY GENRE:");

        return view;
    }

    private List<Filtro> cargarListaDeFiltros() {
        listaDeFiltros.add(new Filtro("ACTION",R.drawable.pocho_rambo,"28"));
        listaDeFiltros.add(new Filtro("ANIMATION",R.drawable.pocho_animado,"16"));
        listaDeFiltros.add(new Filtro("COMEDY",R.drawable.pocho_payaso,"35"));
        listaDeFiltros.add(new Filtro("CRIME",R.drawable.pocho_comedia,"80"));
        listaDeFiltros.add(new Filtro("DRAMA",R.drawable.pocho_grita,"18"));
        listaDeFiltros.add(new Filtro("HORROR",R.drawable.pocho_terror,"27"));
        listaDeFiltros.add(new Filtro("ROMANCE",R.drawable.pocho_romantico,"10749"));
        listaDeFiltros.add(new Filtro("SCIENCE FICTION",R.drawable.pocho_star_war,"878"));
        listaDeFiltros.add(new Filtro("MYSTERY",R.drawable.pocho_pregunta,"9648"));

        //listaDeFiltros.add(new Filtro("HISTORY",R.drawable.pocho_grita,"36"));
        //listaDeFiltros.add(new Filtro("FAMILY",R.drawable.pocho_romantico,"10751"));
        //listaDeFiltros.add(new Filtro("TV MOVIE",R.drawable.pocho_grita,"10770"));
        //listaDeFiltros.add(new Filtro("THRILLER",R.drawable.pocho_saludando,"53"));
        //listaDeFiltros.add(new Filtro("WAR",R.drawable.pocho_alone,"10752"));
        //listaDeFiltros.add(new Filtro("WESTERN",R.drawable.pocho_terror,"37"));
        //listaDeFiltros.add(new Filtro("DOCUMENTARY",R.drawable.pocho_alone,"99"));
        //listaDeFiltros.add(new Filtro("MUSIC",R.drawable.pocho_alone,"10402"));
        //listaDeFiltros.add(new Filtro("FANTASY",R.drawable.pocho_pregunta,"14"));
        //listaDeFiltros.add(new Filtro("ADVENTURE",R.drawable.pocho_romantico,"12"));

        return listaDeFiltros;
    }

    @Override
    public void filtroAplicado(Filtro unFiltro) {
        this.getDialog().dismiss();
        notificadorDeFiltroEnActivity.filtroSeleccionadoEnActivity(unFiltro);
    }

    public interface NotificadorDeFiltroEnActivity{
        public void filtroSeleccionadoEnActivity(Filtro filtro);
    }
}
