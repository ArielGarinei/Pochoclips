package com.example.user.pochoclips.views.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DH on 1/6/2018.
 */

public class TabAdapterPeliculasFragment extends FragmentPagerAdapter{

    private List<Fragment> listaDeFragments;
    private List<CharSequence> nombresDeLosTabs;

    public TabAdapterPeliculasFragment(FragmentManager fm, List<Fragment> listaDeFragments) {
        super(fm);
        this.listaDeFragments = listaDeFragments;
        nombresDeLosTabs = new ArrayList<>();
        //Los nombres de los tabs tienen que estar en orden
        nombresDeLosTabs.add("HOME");
        nombresDeLosTabs.add("FAVORITES");
        nombresDeLosTabs.add("RECOMMENDED");
    }

    @Override
    public Fragment getItem(int position) {
        return this.listaDeFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return this.nombresDeLosTabs.get(position);
    }

    @Override
    public int getCount() {
        return this.listaDeFragments.size();
    }
}
