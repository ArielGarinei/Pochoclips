package com.example.user.pochoclips.views.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MotionEvent;

import com.example.user.pochoclips.models.POJO.Movie;
import com.example.user.pochoclips.views.fragments.FragmentDetalle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ariel on 12/6/2018.
 */

public class ViewPagerAdapterDetalle extends FragmentPagerAdapter {
    List<Fragment> fragmentList = new ArrayList<>();


    public ViewPagerAdapterDetalle(FragmentManager fm, List<Movie> movieList) {
        super(fm);
        for (Movie movieEnPosicion : movieList) {
            fragmentList.add(FragmentDetalle.crearDetalleFragment(movieEnPosicion));
        }
    }

    @Override
    public Fragment getItem(int position) {

        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }



}
