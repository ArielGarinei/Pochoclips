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
import com.example.user.pochoclips.models.POJO.Filtro;
import com.example.user.pochoclips.models.POJO.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DH on 21/7/2018.
 */

public class RecyclerViewAdapterPeliculasFiltradas extends RecyclerView.Adapter {
    private Context context;
    private List<Movie> listaDePeliculasFiltradas;
    private EscuchadorDePeliculaFiltrada escuchadorDePeliculaFiltrada;

    public RecyclerViewAdapterPeliculasFiltradas(EscuchadorDePeliculaFiltrada escuchadorDePeliculaFiltrada) {
        this.listaDePeliculasFiltradas = new ArrayList<>();
        this.escuchadorDePeliculaFiltrada = escuchadorDePeliculaFiltrada;
    }

    public void setListaDePeliculasFiltradas(List<Movie> listaDePeliculasFiltradas) {
        this.listaDePeliculasFiltradas = listaDePeliculasFiltradas;
    }

    public void agregarAListaDePeliculasFiltradas(List<Movie> movieList){
        this.listaDePeliculasFiltradas.addAll(movieList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View celdaView = layoutInflater.inflate(R.layout.celda_pelicula_favoritos, parent, false);
        ViewHolderPeliculasFiltradas viewHolderPeliculasFiltradas = new ViewHolderPeliculasFiltradas(celdaView);

        return viewHolderPeliculasFiltradas;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movie movieFiltradaEnPosicion = listaDePeliculasFiltradas.get(position);
        ViewHolderPeliculasFiltradas viewHolderPeliculasFiltradas = (ViewHolderPeliculasFiltradas) holder;
        viewHolderPeliculasFiltradas.bindPelicula(movieFiltradaEnPosicion);
    }

    @Override
    public int getItemCount() {
        return listaDePeliculasFiltradas.size();
    }

    private class ViewHolderPeliculasFiltradas extends RecyclerView.ViewHolder{
        private ImageView imagenPelicula;

        public ViewHolderPeliculasFiltradas(View itemView) {
            super(itemView);
            imagenPelicula = itemView.findViewById(R.id.imageViewImagenDeLaPeliculaRecyclerFavoritos);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer posicionPeliculaFiltradaSeleccionada = getAdapterPosition();
                    escuchadorDePeliculaFiltrada.informarPeliculaFiltradaElegida(posicionPeliculaFiltradaSeleccionada,listaDePeliculasFiltradas);
                }
            });
        }

        public void bindPelicula(Movie unaMovie){
            Glide.with(context).load(TMDBHelper.getImagePoster(TMDBHelper.IMAGE_SIZE_W300 , unaMovie.getPosterPath())).into(imagenPelicula);
        }
    }

    public interface EscuchadorDePeliculaFiltrada {
        public void informarPeliculaFiltradaElegida(Integer posicion,List<Movie> listaDePeliculasFIltradas);
    }
}
