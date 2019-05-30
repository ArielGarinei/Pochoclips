package com.example.user.pochoclips.views.fragments.tab_layout.recycler_view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.pochoclips.HTTPConectionManager;
import com.example.user.pochoclips.R;
import com.example.user.pochoclips.controllers.ControladorFavoritosRoom;
import com.example.user.pochoclips.controllers.ControladorMovie;
import com.example.user.pochoclips.controllers.ControladorRecomendados;
import com.example.user.pochoclips.controllers.ControladorRecomendadosRoom;
import com.example.user.pochoclips.interfaces.ResultListener;
import com.example.user.pochoclips.models.POJO.Movie;
import com.example.user.pochoclips.models.POJO.Recomendacion;
import com.example.user.pochoclips.models.database.DataBaseFavoritos;
import com.example.user.pochoclips.models.database.DataBaseRecomendaciones;
import com.example.user.pochoclips.views.adapter.RecyclerViewAdapterInicio;
import com.example.user.pochoclips.views.adapter.RecyclerViewAdapterRecomendaciones;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class    RecyclerViewFragmentRecomendados extends Fragment implements RecyclerViewAdapterRecomendaciones.EscuchadorDeRecomendacion{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewAdapterRecomendaciones recyclerViewAdapterRecomendaciones;
    private NotificadorDeRecomendaciones notificadorDeRecomendaciones;


    private FirebaseAuth firebaseAuth;
    private FirebaseUser usuarioActual;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceRating;
    private FirebaseStorage firebaseStorage;



    public RecyclerViewFragmentRecomendados() {

        // Required empty public constructor
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notificadorDeRecomendaciones = (NotificadorDeRecomendaciones) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler_view_fragment_recomendados, container, false);

        firebaseStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        usuarioActual = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        recyclerView = view.findViewById(R.id.contenedorDeRecomendadosRecycler);
        CargarRecomendaciones();

        return view;
    }

    public void CargarRecomendaciones(){
        if (usuarioActual != null){

            ControladorRecomendados controladorRecomendados = new ControladorRecomendados(getContext());
            controladorRecomendados.ObtenerRecomendados(new ResultListener<List<Recomendacion>>() {
                @Override
                public void finish(List<Recomendacion> resultado) {
                    List<Recomendacion> recomendacionList = resultado;
                        recyclerViewAdapterRecomendaciones = new RecyclerViewAdapterRecomendaciones(recomendacionList,RecyclerViewFragmentRecomendados.this);
                        recyclerView.setAdapter(recyclerViewAdapterRecomendaciones);
                        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                        recyclerView.setLayoutManager(layoutManager);
                    }
            },usuarioActual.getUid());
        }
    }

    @Override
    public void seleccionaronLaRecomendacion(int posicionRecomendacion, List<Recomendacion> recomendacionList) {
        notificadorDeRecomendaciones.seleccionaronLaRecomendacion(posicionRecomendacion,recomendacionList);

    }

    public interface NotificadorDeRecomendaciones{
        public void  seleccionaronLaRecomendacion(int posicionRecomendacion, List<Recomendacion> recomendacionList);

    }
}
