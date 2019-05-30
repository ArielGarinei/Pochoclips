package com.example.user.pochoclips.views.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user.pochoclips.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityLoading extends AppCompatActivity {

    private FirebaseUser usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        usuarioActual = FirebaseAuth.getInstance().getCurrentUser();

        //Todo Cargar las peliculas y pasarlas a los recyclers del ActivityInicio

        if (usuarioActual == null) {
            Intent intent = new Intent(ActivityLoading.this, ActivityIntroduccion.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(ActivityLoading.this, ActivityInicio.class);
            startActivity(intent);
            finish();
        }
    }

}
