package com.example.user.pochoclips.views.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.user.pochoclips.R;
import com.example.user.pochoclips.TMDBHelper;
import com.example.user.pochoclips.controllers.ControladorMovie;
import com.example.user.pochoclips.interfaces.ResultListener;
import com.example.user.pochoclips.models.POJO.Movie;
import com.example.user.pochoclips.models.POJO.Recomendacion;

import java.util.List;

/**
 * Created by DH on 18/7/2018.
 */

public class RecyclerViewAdapterRecomendaciones extends RecyclerView.Adapter {
    private List<Recomendacion> recomendacionList;
    private List<Movie> movieList;
    private EscuchadorDeRecomendacion escuchadorDeRecomendacion;
    private Context context;

    public RecyclerViewAdapterRecomendaciones(List<Recomendacion> recomendacionList, EscuchadorDeRecomendacion escuchadorDeRecomendacion) {
        this.recomendacionList = recomendacionList;
        this.escuchadorDeRecomendacion = escuchadorDeRecomendacion;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View celda = layoutInflater.inflate(R.layout.celda_recomendados, parent,false);
        RecomendacionesViewHolder recomendacionesViewHolder = new RecomendacionesViewHolder(celda);
        return recomendacionesViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Recomendacion recomendacionEnPosicion = recomendacionList.get(position);
        RecomendacionesViewHolder recomendacionesViewHolder = (RecomendacionesViewHolder) holder;
        recomendacionesViewHolder.bindRecomendaciones(recomendacionEnPosicion);


    }

    @Override
    public int getItemCount() {
        return recomendacionList.size();
    }

    public class RecomendacionesViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageViewFotoUsuario;
        private ImageView imageViewPeliculaRecomendada;
        private TextView textViewNombreDeUsuarioRecomendador;
        private TextView textViewPuntuacionUsuarioRecomendador;
        private TextView textViewNombreDeLaPelicula;
        private TextView textViewPuntuacionDeLaPelicula;
        private TextView textViewOverviewRecomendacion;

        public RecomendacionesViewHolder(View itemView) {
            super(itemView);
            imageViewFotoUsuario = itemView.findViewById(R.id.imageViewFotoUsuario);
            imageViewPeliculaRecomendada = itemView.findViewById(R.id.imageViewPeliculaRecomendada);
            textViewNombreDeUsuarioRecomendador = itemView.findViewById(R.id.textViewNombreDeUsuario);
            textViewPuntuacionUsuarioRecomendador = itemView.findViewById(R.id.textViewPuntuacionUsuarioRecomendador);
            textViewNombreDeLaPelicula = itemView.findViewById(R.id.textViewNombreDeLaPelicula);
            textViewPuntuacionDeLaPelicula = itemView.findViewById(R.id.textViewPuntuacionDeLaPelicula);
            textViewOverviewRecomendacion = itemView.findViewById(R.id.textViewOverviewRecomendacion);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int posicion = getAdapterPosition();
                    escuchadorDeRecomendacion.seleccionaronLaRecomendacion(posicion,recomendacionList);
                }
            });
        }

        public void bindRecomendaciones(Recomendacion recomendacion){

            RequestOptions options = new RequestOptions().placeholder(R.drawable.loading);
            Glide.with(context).load(TMDBHelper.getImagePoster(TMDBHelper.IMAGE_SIZE_W1280, recomendacion.getBackdropPath())).apply(options).into(imageViewPeliculaRecomendada);
            Glide.with(context).load(recomendacion.getUrlFotoRecomendador()).apply(RequestOptions.circleCropTransform()).into(imageViewFotoUsuario);
            textViewNombreDeUsuarioRecomendador.setText(recomendacion.getNombreRecomendador());
            //textViewPuntuacionUsuarioRecomendador.setText(recomendacion.getIdRecomendador());
            textViewNombreDeLaPelicula.setText(recomendacion.getTitle());
            textViewPuntuacionDeLaPelicula.setText(recomendacion.getVoteAverage().toString());
            textViewOverviewRecomendacion.setText(recomendacion.getOverview());


        }

    }

    public interface EscuchadorDeRecomendacion{
        public void seleccionaronLaRecomendacion(int posicionRecomendacion, List<Recomendacion> recomendacionList);
    }


}
