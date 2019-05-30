package com.example.user.pochoclips.views.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.pochoclips.R;
import com.example.user.pochoclips.TMDBHelper;
import com.example.user.pochoclips.models.POJO.Imagen;

import java.util.List;

/**
 * Created by User on 25/7/2018.
 */

public class RecyclerViewAdapterImagenes extends RecyclerView.Adapter {

    private List<Imagen> listaDeImagenes;
    private Context contexto;

    public RecyclerViewAdapterImagenes(List<Imagen> listaDeImagenes, Context contexto) {
        this.listaDeImagenes = listaDeImagenes;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View celdaView = layoutInflater.inflate(R.layout.celda_imagen, parent, false);
        ViewHolderImagen viewHolderImagen = new ViewHolderImagen(celdaView);
        return viewHolderImagen;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        
        Imagen imagenEnPosicion = listaDeImagenes.get(position);
        ViewHolderImagen viewHolderImagen = (ViewHolderImagen) holder;
        viewHolderImagen.asignarImagen(imagenEnPosicion);
    }

    @Override
    public int getItemCount() {
        return listaDeImagenes.size();
    }

    private class ViewHolderImagen extends RecyclerView.ViewHolder {
        private ImageView imageViewImagenCelda;

        public ViewHolderImagen(View itemView) {
            super(itemView);
            imageViewImagenCelda = itemView.findViewById(R.id.imageViewImagen_CeldaImagen);
        }

        public void asignarImagen(Imagen imagen) {
            Glide.with(contexto).load(TMDBHelper.getImagePoster(TMDBHelper.IMAGE_SIZE_W780, imagen.getFilePath())).into(imageViewImagenCelda);
        }
    }

}
