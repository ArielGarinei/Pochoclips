package com.example.user.pochoclips.views.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user.pochoclips.R;
import com.example.user.pochoclips.views.fragments.FragmentPerfil;

public class ActivityPerfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentPerfil fragmentPerfil = new FragmentPerfil();
        fragmentTransaction.replace(R.id.contenedorDePerfil, fragmentPerfil);
        fragmentTransaction.commit();

    }
}
