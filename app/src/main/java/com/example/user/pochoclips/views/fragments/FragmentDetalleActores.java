package com.example.user.pochoclips.views.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.pochoclips.R;
import com.example.user.pochoclips.TMDBHelper;
import com.example.user.pochoclips.controllers.ControladorPeople;
import com.example.user.pochoclips.interfaces.ResultListener;
import com.example.user.pochoclips.models.POJO.Cast;
import com.example.user.pochoclips.models.POJO.People;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetalleActores extends Fragment {
    private static final String SERIALIZABLE = "posicion";
    private ImageView imageViewFotoActor;
    private TextView textViewNombre;
    private TextView textViewBiografia;
    private TextView textViewNacimiento;
    private TextView textViewMuerte;
    private TextView textViewPlaceOfBirth;
    private TextView textViewHomepage;
    private CardView cardViewMuerte;
    private CardView cardViewHomepage;
    private CardView cardViewPlaceOfBirth;
    private People actor;


    public static FragmentDetalleActores crearDetalleFragmentActores(Cast cast) {
        //esto es lo que se usa en el viewPagerAdapter para crear el fragmento apartir de la posicion en la lista.
        FragmentDetalleActores fragmentDetalleActores = new FragmentDetalleActores();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SERIALIZABLE, cast);
        fragmentDetalleActores.setArguments(bundle);

        return fragmentDetalleActores;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_actores, container, false);
        Bundle bundle = getArguments();
        Cast cast = (Cast) bundle.getSerializable(SERIALIZABLE);

        imageViewFotoActor = view.findViewById(R.id.imageViewFotoActor_FragmentDetalleActores);
        textViewNombre = view.findViewById(R.id.textViewNombreActor_FragmentDetalleActores);
        textViewBiografia = view.findViewById(R.id.textViewBiografiaActor_FragmentDetalleActores);
        textViewNacimiento = view.findViewById(R.id.textViewNacimientoActor_FragmentDetalleActores);
        textViewHomepage = view.findViewById(R.id.textViewHomepageActor_FragmentDetalleActores);
        textViewMuerte = view.findViewById(R.id.textViewMuerteActor_FragmentDetalleActores);
        textViewPlaceOfBirth = view.findViewById(R.id.textViewPlaceOfBirthActor_FragmentDetalleActores);
        cardViewPlaceOfBirth = view.findViewById(R.id.cardViewPlaceOfBirthActor_FragmentDetalleActores);
        cardViewMuerte = view.findViewById(R.id.cardViewMuerteActor_FragmentDetalleActores);
        cardViewHomepage = view.findViewById(R.id.cardViewHomepageActor_FragmentDetalleActores);

        Glide.with(getContext()).load(TMDBHelper.getImagePoster(TMDBHelper.IMAGE_SIZE_W1280,cast.getProfilePath())).into(imageViewFotoActor);


        ControladorPeople controladorPeople = new ControladorPeople(getContext());
        controladorPeople.ObtenerPersonas(new ResultListener<People>() {

            @Override
            public void finish(People resultado) {
                actor = resultado;
                setearDatosDelActor();
            }
        },cast.getId().toString());

        return view;
    }

    public void setearDatosDelActor(){

        //TODO: if actor.getProfilePath() == null ...

        String nombre = actor.getName();
        String birthday = actor.getBirthday();
        final String homepage = actor.getHomepage();
        String deathDay = actor.getDeathday();
        String biography = actor.getBiography();
        String placeOfBirth = actor.getPlace_of_birth();


        textViewNombre.setText(nombre);

        if (biography != null){
            textViewBiografia.setText(biography);
        }

        if (birthday != null){
            textViewNacimiento.setText(birthday);
        }

        if (homepage != null){
            cardViewHomepage.setVisibility(View.VISIBLE);
            textViewHomepage.setText(homepage);
            textViewHomepage.setMovementMethod(LinkMovementMethod.getInstance());
            textViewHomepage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view)
                {
                    Intent abrirBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(homepage));
                    startActivity(abrirBrowser);
                }
            });
        }

        if (deathDay != null){
            cardViewMuerte.setVisibility(View.VISIBLE);
            textViewMuerte.setText(deathDay);
        }

        if (placeOfBirth != null){
            cardViewPlaceOfBirth.setVisibility(View.VISIBLE);
            textViewPlaceOfBirth.setText(placeOfBirth);
        }

    }

}
