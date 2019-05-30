package com.example.user.pochoclips.views.fragments.tab_layout.recycler_view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.pochoclips.R;
import com.example.user.pochoclips.controllers.ControladorFavoritos;
import com.example.user.pochoclips.controllers.ControladorFavoritosRoom;
import com.example.user.pochoclips.controllers.ControladorMovie;
import com.example.user.pochoclips.views.adapter.RecyclerViewAdapterFavoritos;
import com.example.user.pochoclips.interfaces.ResultListener;
import com.example.user.pochoclips.models.POJO.Movie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerViewFragmentFavoritos extends Fragment implements RecyclerViewAdapterFavoritos.EscuchadorDePeliculasFab {
    private NotificadorDeActivitiesFab notificadorDeActivitiesFab;
    private RecyclerView recyclerView;


    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private RecyclerView.LayoutManager layoutManager;

    public RecyclerViewFragmentFavoritos() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notificadorDeActivitiesFab = (NotificadorDeActivitiesFab) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewFavoritos);

        layoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseStorage = FirebaseStorage.getInstance();

        cargarFavoritos();

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        cargarFavoritos();
    }

    public void cargarFavoritos(){
        ControladorFavoritosRoom controladorFavoritosRoom = new ControladorFavoritosRoom(getContext());
        List<Movie> todasLasFavoritas = controladorFavoritosRoom.getFavoritos();
        if (todasLasFavoritas.isEmpty()){
            retroalimentarRoomConFirebase();
        }
        RecyclerViewAdapterFavoritos recyclerViewAdapterFavoritos = new RecyclerViewAdapterFavoritos(todasLasFavoritas,RecyclerViewFragmentFavoritos.this);
        recyclerView.setAdapter(recyclerViewAdapterFavoritos);
    }
    public void retroalimentarRoomConFirebase(){
        if (firebaseUser != null) {
            ControladorFavoritos controladorFavoritos = new ControladorFavoritos(getContext(), firebaseUser.getUid());
            controladorFavoritos.pedirFavoritosFirebase();
        }
    }

    @Override
    public void seleccionaronLaPeliculaFab(int posicion,List<Movie> movieList) {
        notificadorDeActivitiesFab.seleccionaronLaPeliculaFab(posicion,movieList);
    }

    public interface NotificadorDeActivitiesFab {
        public void seleccionaronLaPeliculaFab(int posicion,List<Movie> movieList);
    }
}
