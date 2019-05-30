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
import com.example.user.pochoclips.models.POJO.Cast;
import com.example.user.pochoclips.models.POJO.Movie;
import com.example.user.pochoclips.models.POJO.People;

import java.util.List;

/**
 * Created by Arielo on 20/6/2018.
 */

public class RecyclerViewAdapterDetalleActores extends RecyclerView.Adapter {
    private List<Cast> castList;
    private Context context;
    private EscuchadorDeActores escuchadorDeActores;

    public RecyclerViewAdapterDetalleActores(List<Cast> castList, EscuchadorDeActores escuchadorDeActores) {
        this.castList = castList;
        this.escuchadorDeActores = escuchadorDeActores;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View celda = layoutInflater.inflate(R.layout.celda_actores,parent,false);
        ViewHolderDetalleActores viewHolderDetalleActores = new ViewHolderDetalleActores(celda);
        return viewHolderDetalleActores;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Cast castEnPosicion = castList.get(position);
        ViewHolderDetalleActores viewHolderDetalleActores = (ViewHolderDetalleActores) holder;
        viewHolderDetalleActores.bindActores(castEnPosicion);
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }


    private class ViewHolderDetalleActores extends RecyclerView.ViewHolder{
        ImageView imageViewActor;
        TextView textViewNombreActor;
        TextView textViewNombrePersonaje;
        public ViewHolderDetalleActores(View itemView) {
            super(itemView);
            imageViewActor = itemView.findViewById(R.id.imageViewActorCelda);
            textViewNombreActor = itemView.findViewById(R.id.textViewCeldaNombreActor);
            textViewNombrePersonaje = itemView.findViewById(R.id.textViewCeldaNombrePersonaje);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int posicion = getAdapterPosition();
                    escuchadorDeActores.seleccionaronAlActor(posicion,castList);

                }
            });
        }
        public void bindActores(Cast actorenPosicion){
            RequestOptions options = new RequestOptions();
            options.circleCrop();
            Glide.with(context).load(TMDBHelper.getImagePoster(TMDBHelper.IMAGE_SIZE_W185,actorenPosicion.getProfilePath())).apply(options).into(imageViewActor);
            textViewNombreActor.setText(actorenPosicion.getName());
            textViewNombrePersonaje.setText(actorenPosicion.getCharacter());
        }

    }
    public interface EscuchadorDeActores{
        public void seleccionaronAlActor(int posicion,List<Cast> castList);
    }
}
