package com.example.user.pochoclips.views.fragments.view_pager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.pochoclips.R;
import com.example.user.pochoclips.TMDBHelper;
import com.example.user.pochoclips.controllers.ControladorMovie;
import com.example.user.pochoclips.views.adapter.ViewPagerAdapterDetalle;
import com.example.user.pochoclips.models.POJO.Movie;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPageFragmentDetalle extends Fragment {

    private ViewPager viewPager;
    private ControladorMovie controladorMovie;
    private ViewPagerAdapterDetalle viewPagerAdapterDetalle;

    public ViewPageFragmentDetalle() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_page_fragment_detalle, container, false);
        /*Bundle bundle = getArguments();
        Integer posicion = bundle.getInt(TMDBHelper.POSICION);
        List<Movie> movieList = (List<Movie>) bundle.getSerializable(TMDBHelper.SERIALIZABLE);
        viewPager = view.findViewById(R.id.viewPager);
        viewPagerAdapterDetalle = new ViewPagerAdapterDetalle(getFragmentManager(), movieList);
        viewPager.setAdapter(viewPagerAdapterDetalle);
        viewPager.setCurrentItem(posicion);*/

        return view;
    }

}
