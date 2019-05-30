package com.example.user.pochoclips.views.fragments.recyclerView;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.pochoclips.R;
import com.example.user.pochoclips.models.POJO.User;
import com.example.user.pochoclips.views.adapter.RecyclerViewAdapterSeleccionarUsuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.user.pochoclips.views.adapter.RecyclerViewAdapterSeleccionarUsuario.EscuchadorDelRecyclerViewAdapterSeleccionarUsuario;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerviewFragmentSeleccionarUsuario extends DialogFragment implements EscuchadorDelRecyclerViewAdapterSeleccionarUsuario{
    private RecyclerView recyclerView;
    private RecyclerViewAdapterSeleccionarUsuario adapterRecomendaciones;
    private EscuchadorDelFragmentSeleccionarUsuario escuchadorDelFragmentSeleccionarUsuario;

    public RecyclerviewFragmentSeleccionarUsuario() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_seleccionar_usuario, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewUsuariosSeleccionar);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        escuchadorDelFragmentSeleccionarUsuario = (EscuchadorDelFragmentSeleccionarUsuario) getParentFragment();
        cargarUsers();
        return view;
    }

    private void cargarUsers(){
        DatabaseReference mDatabase;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase= firebaseDatabase.getReference();
        DatabaseReference referencelista = mDatabase.child("TodosLosUsuarios");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> userList = new ArrayList<>();
                for (DataSnapshot mDataSnapshot : dataSnapshot.getChildren()) {
                    User user = mDataSnapshot.getValue(User.class);
                    userList.add(user);
                }

                adapterRecomendaciones = new RecyclerViewAdapterSeleccionarUsuario(userList, RecyclerviewFragmentSeleccionarUsuario.this);
                recyclerView.setAdapter(adapterRecomendaciones);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        referencelista.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public void seleccionaronAlUsuario(String userId) {
        this.getDialog().dismiss();
        escuchadorDelFragmentSeleccionarUsuario.seleccionaronAlUsuario2(userId);
    }
    public interface EscuchadorDelFragmentSeleccionarUsuario{
        public void seleccionaronAlUsuario2(String userId);
    }
}

