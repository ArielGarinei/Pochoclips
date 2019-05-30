package com.example.user.pochoclips.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.pochoclips.TMDBHelper;
import com.example.user.pochoclips.models.POJO.Movie;
import com.example.user.pochoclips.R;

import java.util.List;

/**
 * Created by User on 28/5/2018.
 */

public class RecyclerViewAdapterInicio extends RecyclerView.Adapter {

    private List<Movie> movieList;
    private EscuchadorDePeliculas escuchadorDePeliculas;
    private Context context;

    public RecyclerViewAdapterInicio(List<Movie> movieList, EscuchadorDePeliculas escuchadorDePeliculas) {
        this.movieList = movieList;
        this.escuchadorDePeliculas = escuchadorDePeliculas;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View celda = layoutInflater.inflate(R.layout.celda_pelicula_inicio, parent, false);

        PeliculaViewHolder peliculaViewHolder = new PeliculaViewHolder(celda);

        return peliculaViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Movie movieEnPosicion = movieList.get(position);
        PeliculaViewHolder peliculaViewHolder = (PeliculaViewHolder) holder;

        peliculaViewHolder.asignarPelicula(movieEnPosicion);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    private class PeliculaViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewImagenPelicula;
        private TextView textViewNombrePelicula;

        public PeliculaViewHolder(View itemView) {
            super(itemView);
            imageViewImagenPelicula = itemView.findViewById(R.id.imageViewImagenDeLaPelicula_Recycler);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Integer posicion = getAdapterPosition();
                    escuchadorDePeliculas.seleccionaronLaPelicula(posicion, movieList);

                }
            });

        }

        public void asignarPelicula(Movie unaMovie) {
            Glide.with(context).load(TMDBHelper.getImagePoster(TMDBHelper.IMAGE_SIZE_W300 , unaMovie.getPosterPath())).into(imageViewImagenPelicula);
        }
    }

    public interface EscuchadorDePeliculas {
        public void seleccionaronLaPelicula(int posicion,List<Movie> movieList);
    }
}


