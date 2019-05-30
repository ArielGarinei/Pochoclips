package com.example.user.pochoclips.views.fragments.view_pager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.pochoclips.R;
import com.example.user.pochoclips.TMDBHelper;
import com.example.user.pochoclips.controllers.ControladorCast;
import com.example.user.pochoclips.models.POJO.Cast;
import com.example.user.pochoclips.views.adapter.ViewPagerAdapterDetalleActores;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragmentDetalleActores extends Fragment {
    private ViewPager viewPager;
    private ControladorCast controladorCast;
    private ViewPagerAdapterDetalleActores viewPagerAdapterDetalleActores;

    private static final String SERIALIZABLE = "serializable";
    private static final String POSICION = "posicion";




    public ViewPagerFragmentDetalleActores() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager_detalle_actores, container, false);
        Bundle bundle = getArguments();
        Integer posicion = bundle.getInt(TMDBHelper.POSICION);
        List<Cast> castList = (List<Cast>) bundle.getSerializable(TMDBHelper.SERIALIZABLE);
        viewPager = view.findViewById(R.id.viewPagerDetalleActores);
        viewPagerAdapterDetalleActores = new ViewPagerAdapterDetalleActores(getFragmentManager(),castList);
        viewPager.setAdapter(viewPagerAdapterDetalleActores);
        viewPager.setCurrentItem(posicion);
        return view;
    }

}
