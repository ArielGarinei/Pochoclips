package com.example.user.pochoclips.views.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.user.pochoclips.R;
import com.example.user.pochoclips.TMDBHelper;
import com.example.user.pochoclips.models.POJO.Movie;

import java.util.List;

/**
 * Created by Arielo on 17/6/2018.
 */

public class RecyclerViewAdapterFavoritos extends RecyclerView.Adapter {
    private List<Movie> movieList;
    private Context context;
    private EscuchadorDePeliculasFab escuchadorDePeliculasFab;

    public RecyclerViewAdapterFavoritos(List<Movie> movieList, EscuchadorDePeliculasFab escuchadorDePeliculasFab) {
        this.movieList = movieList;
        this.escuchadorDePeliculasFab = escuchadorDePeliculasFab;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View celda = layoutInflater.inflate(R.layout.celda_pelicula_favoritos,parent,false);
        ViewHolderPeliculaFavoritos viewHolderPeliculaFavoritos = new ViewHolderPeliculaFavoritos(celda);
        return viewHolderPeliculaFavoritos;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movie movieEnPosicion = movieList.get(position);
        ViewHolderPeliculaFavoritos viewHolderPeliculaFavoritos = (ViewHolderPeliculaFavoritos) holder;
        viewHolderPeliculaFavoritos.bindFavoritos(movieEnPosicion);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

//    public void actualizarLista(List<Movie> movieList2){
//        movieList.clear();
//        movieList.addAll(movieList2);
//    }

    private class ViewHolderPeliculaFavoritos extends RecyclerView.ViewHolder{
        private ImageView imageViewPeliculaFavoritos;


        public ViewHolderPeliculaFavoritos(View itemView) {
            super(itemView);
            imageViewPeliculaFavoritos = itemView.findViewById(R.id.imageViewImagenDeLaPeliculaRecyclerFavoritos);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int posicion = getAdapterPosition();
                    escuchadorDePeliculasFab.seleccionaronLaPeliculaFab(posicion,movieList);
                }
            });
        }

        public void bindFavoritos(Movie movieEnPosicion){
            Glide.with(context).load(TMDBHelper.getImagePoster(TMDBHelper.IMAGE_SIZE_W300 , movieEnPosicion.getPosterPath())).into(imageViewPeliculaFavoritos);
        }
    }
    public interface EscuchadorDePeliculasFab {
        public void seleccionaronLaPeliculaFab(int posicion,List<Movie> movieList);
    }
}
