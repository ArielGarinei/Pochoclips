package com.example.user.pochoclips.controllers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.user.pochoclips.HTTPConectionManager;
import com.example.user.pochoclips.interfaces.ResultListener;
import com.example.user.pochoclips.models.POJO.Recomendacion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

/**
 * Created by DH on 18/7/2018.
 */

public class ControladorRecomendados {
    private Context context;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;

    public ControladorRecomendados(Context context) {
        this.context = context;
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }

    public void ObtenerRecomendados(final ResultListener<List<Recomendacion>> escuchadorDeLaVista,String userId){

        if(HTTPConectionManager.isNetworkingOnline(context)){
            DatabaseReference databaseReference;
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();
            DatabaseReference reference = databaseReference.child("User").child(userId).child("Recomendados");

            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<Recomendacion> recomendacionList = new ArrayList<>();
                    ControladorRecomendadosRoom controladorRecomendadosRoom = new ControladorRecomendadosRoom(context);
                    for (DataSnapshot dataSnapshotChild : dataSnapshot.getChildren()){
                        Recomendacion mRecomendacion = dataSnapshotChild.getValue(Recomendacion.class);
                        recomendacionList.add(mRecomendacion);

                        controladorRecomendadosRoom.removeRecomendacion(recomendacionList);
                        controladorRecomendadosRoom.addRecomendacion(recomendacionList);
                    }
                    escuchadorDeLaVista.finish(recomendacionList);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            reference.addValueEventListener(valueEventListener);


        } else {
            ControladorRecomendadosRoom controladorRecomendadosRoom = new ControladorRecomendadosRoom(context);
            escuchadorDeLaVista.finish(controladorRecomendadosRoom.getRecomendacion());
        }
    }
}