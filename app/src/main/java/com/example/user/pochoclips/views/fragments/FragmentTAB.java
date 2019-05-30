package com.example.user.pochoclips.views.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.pochoclips.R;
import com.example.user.pochoclips.models.POJO.ViewPagerSinSwipe;
import com.example.user.pochoclips.views.adapter.TabAdapterPeliculasFragment;
import com.example.user.pochoclips.views.fragments.tab_layout.recycler_view.RecyclerViewFragmentFavoritos;
import com.example.user.pochoclips.views.fragments.tab_layout.recycler_view.RecyclerViewFragmentInicio;
import com.example.user.pochoclips.views.fragments.tab_layout.recycler_view.RecyclerViewFragmentRecomendados;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTAB extends Fragment {

    private ViewPagerSinSwipe viewPagerTAB;
    private List<Fragment> listaDeFragments;

    private TabAdapterPeliculasFragment tabAdapterPeliculasFragment;

    public FragmentTAB() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_peliculas, container, false);

        //-------TAB--------
        //Busco en la view el TAB
        viewPagerTAB = (ViewPagerSinSwipe) view.findViewById(R.id.viewPager_TAB_FragmentPeliculas);
        //Creo el adapter TAB y le paso por parametro el SFM y la lista de fragmentos a mostrar
        tabAdapterPeliculasFragment = new TabAdapterPeliculasFragment(getChildFragmentManager(), getListaDeFragments());
        //Al TAB le seteo su adaptador
        viewPagerTAB.setAdapter(tabAdapterPeliculasFragment);
        //Busco el TAB Layout en la view
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout_FragmentPeliculas);
        //Lamo a esta mierda y le paso el viewpagerTAB
        tabLayout.setupWithViewPager(viewPagerTAB);
        viewPagerTAB.setOffscreenPageLimit(2);

        return view;
    }


    //Esto va en adaptador del TAB
    public List<Fragment> getListaDeFragments() {
        //Creo una lista de fragmentos
        listaDeFragments = new ArrayList<>();
        //Y le agrego los fragmentos a mostrar
        listaDeFragments.add(new RecyclerViewFragmentInicio());
        listaDeFragments.add(new RecyclerViewFragmentFavoritos());
        listaDeFragments.add(new RecyclerViewFragmentRecomendados());

        return listaDeFragments;
    }

}
