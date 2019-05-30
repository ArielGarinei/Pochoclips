package com.example.user.pochoclips.views.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.user.pochoclips.R;
import com.hololo.tutorial.library.PermissionStep;
import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public class ActivityIntroduccion extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int colorPrimario = getResources().getColor(R.color.colorPrimary);

        addFragment(new Step.Builder().setTitle("Esto es PochoClips")
                .setContent("Bienvenido tu nueva APP de peliculas")
                .setBackgroundColor(colorPrimario) // int background color
                .setDrawable(R.drawable.on_boarding_logo) // int top drawable
                .setSummary("continua y conocenos más")
                .build());

        addFragment(new Step.Builder().setTitle("Busca tus peliculas favoritas")
                .setContent("Encuentra todas tus peliculas con los generos que más te gusten")
                .setBackgroundColor(colorPrimario) // int background color
                .setDrawable(R.drawable.on_boarding_uno) // int top drawable
                .setSummary("continua y conocenos más")
                .build());

        addFragment(new Step.Builder().setTitle("Descubre más de tus preferidas")
                .setContent("Puedes ver trailers presionando el botón de PLAY, " +
                        "ver el puntaje y los actores de todas tus pelis")
                .setBackgroundColor(colorPrimario) // int background color
                .setDrawable(R.drawable.on_boarding_dos) // int top drawable
                .setSummary("continua y conocenos más")
                .build());

        addFragment(new Step.Builder().setTitle("¡Recomiendala a tus amigos!")
                .setContent("Con tan solo un clic, puedes recomendar tus peliculas preferidas a tus amigos")
                .setBackgroundColor(colorPrimario) // int background color
                .setDrawable(R.drawable.on_boarding_tres) // int top drawable
                .setSummary("Presiona finalizar y empieza a usar Pochoclips ahora")
                .build());
    }

    @Override
    public void finish() {
        super.finish();
        startActivity(new Intent(this,ActivityInicio.class));
    }

}
