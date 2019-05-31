package com.example.user.pochoclips.views.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.user.pochoclips.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPerfil extends Fragment {

    private ImageView imageViewFotoPerfil;
    private TextView textViewNombreUsuario;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser usuarioActual;
    private Bitmap fotoPerfilUsuario;


    public FragmentPerfil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        usuarioActual = firebaseAuth.getCurrentUser();


        textViewNombreUsuario = view.findViewById(R.id.textViewNombreUsuario_PerfilFragment);
        imageViewFotoPerfil = view.findViewById(R.id.imageViewFotoPerfil_PerfilFragment);

        textViewNombreUsuario.setText(usuarioActual.getDisplayName());
        Glide.with(getContext()).load(usuarioActual.getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(imageViewFotoPerfil);
        //TODO: USAR EL "/picture?type=large" PARA TRAER LA IMAGEN GRANDE (CON FB ANDA PERO CON GOOGLE NO)

        return view;
    }


}
