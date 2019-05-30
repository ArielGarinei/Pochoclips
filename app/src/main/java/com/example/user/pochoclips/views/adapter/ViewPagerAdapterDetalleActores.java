package com.example.user.pochoclips.views.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.user.pochoclips.models.POJO.Cast;
import com.example.user.pochoclips.views.fragments.FragmentDetalleActores;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arielo on 8/7/2018.
 */

public class ViewPagerAdapterDetalleActores extends FragmentPagerAdapter {
    List<Fragment> fragmentList = new ArrayList<>();



    public ViewPagerAdapterDetalleActores(FragmentManager fm,List<Cast> castList) {
        super(fm);
        for (Cast castEnposicion : castList) {
            fragmentList.add(FragmentDetalleActores.crearDetalleFragmentActores(castEnposicion));
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
