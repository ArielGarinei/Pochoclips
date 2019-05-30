package com.example.user.pochoclips.controllers;

import android.content.Context;

import com.example.user.pochoclips.HTTPConectionManager;
import com.example.user.pochoclips.interfaces.ResultListener;
import com.example.user.pochoclips.models.POJO.Movie;
import com.example.user.pochoclips.models.database.DataBaseFavoritos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aleja on 22/07/2018.
 */

public class ControladorFavoritos {
    private Context context;
    private String userId;
    private Movie movie;

    public ControladorFavoritos(Context context, String userId, Movie movie) {
        this.context = context;
        this.userId = userId;
        this.movie = movie;
    }

    public ControladorFavoritos(Context context, String userId) {
        this.context = context;
        this.userId = userId;
    }
    public void pediFavoritos(){

    }

    public void guardarFavoritos(){
        if(HTTPConectionManager.isNetworkingOnline(context)){
            ControladorFavoritosRoom controladorFavoritosRoom = new ControladorFavoritosRoom(context);
            controladorFavoritosRoom.addFavoritos(movie.getId());

            DatabaseReference databaseReference;
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("User");
            DatabaseReference reference = databaseReference
                    .child(userId)
                    .child("MisFavoritos")
                    .child(movie.getTitle());
            reference.setValue(movie);
        }
    }

    public void borrarFavoritos(){
        if(HTTPConectionManager.isNetworkingOnline(context)){
            ControladorFavoritosRoom controladorFavoritosRoom = new ControladorFavoritosRoom(context);
            controladorFavoritosRoom.removeFavoritos(movie.getId());

            DatabaseReference databaseReference;
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("User");
            DatabaseReference reference = databaseReference
                    .child(userId)
                    .child("MisFavoritos")
                    .child(movie.getTitle());
            reference.setValue(movie);
        }
    }

    public void borrarFavoritosFirebase(){
        if(HTTPConectionManager.isNetworkingOnline(context)){
            ControladorFavoritosRoom controladorFavoritosRoom = new ControladorFavoritosRoom(context);
            controladorFavoritosRoom.removeFavoritos(movie.getId());

            DatabaseReference databaseReference;
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("User");
            DatabaseReference reference = databaseReference
                    .child(userId)
                    .child("MisFavoritos")
                    .child(movie.getTitle());
            reference.removeValue();
        }
    }

    public void pedirFavoritosFirebase(){
        DatabaseReference databaseReference;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        final DatabaseReference reference = databaseReference.child("User")
                .child(userId)
                .child("MisFavoritos");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshotChild : dataSnapshot.getChildren()){
                    Movie movie = dataSnapshotChild.getValue(Movie.class);
                    ControladorFavoritosRoom controladorFavoritosRoom = new ControladorFavoritosRoom(context);
                    controladorFavoritosRoom.addFavoritos(movie.getId());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(valueEventListener);

    }



}


/*

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();
                    DatabaseReference reference2 = databaseReference.child("User")
                    .child(userId)
                    .child("MisFavoritos");
                    ValueEventListener valueEventListener = new ValueEventListener() {
@Override
public void onDataChange(DataSnapshot dataSnapshot) {
        ControladorFavoritosRoom controladorFavoritosRoom = new ControladorFavoritosRoom(context);
final List<Movie> movieList = new ArrayList<>();
        for (DataSnapshot dataSnapshotChild : dataSnapshot.getChildren()){
        Integer integer= dataSnapshotChild.getValue(Integer.class);
        controladorFavoritosRoom.addFavoritos(integer);

        ControladorMovie controladorMovie = new ControladorMovie(context);
        controladorMovie.ObtenerMoviewDetail(new ResultListener<Movie>() {
@Override
public void finish(Movie resultado) {
        movieList.add(resultado);
        }
        }, integer);
        }
        escuchadorDeLaVista.finish(movieList);
        }
@Override
public void onCancelled(DatabaseError databaseError) {
        }
        };
        reference2.addValueEventListener(valueEventListener);*/
