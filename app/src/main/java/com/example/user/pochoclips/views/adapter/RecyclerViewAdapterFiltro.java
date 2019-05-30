package com.example.user.pochoclips.views.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.pochoclips.R;
import com.example.user.pochoclips.models.POJO.Filtro;

import java.util.List;

public class RecyclerViewAdapterFiltro extends RecyclerView.Adapter {

    private List<Filtro> listaDeGenerosPocho;
    private EscuchadorDeFiltro escuchadorDeFiltro;

    public RecyclerViewAdapterFiltro(List<Filtro> listaDeGenerosPocho, EscuchadorDeFiltro escuchadorDeFiltro) {
        this.listaDeGenerosPocho = listaDeGenerosPocho;
        this.escuchadorDeFiltro = escuchadorDeFiltro;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context contexto = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(contexto);
        View celdaView = layoutInflater.inflate(R.layout.celda_filtro,parent,false);
        ViewHolderFiltro viewHolderFiltro = new ViewHolderFiltro(celdaView);
        return viewHolderFiltro;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Filtro filtroEnPosicion = listaDeGenerosPocho.get(position);
        ViewHolderFiltro viewHolderFiltro = (ViewHolderFiltro) holder;
        viewHolderFiltro.cargaDeFiltro(filtroEnPosicion);
    }

    @Override
    public int getItemCount() {
        return listaDeGenerosPocho.size();
    }

    private class ViewHolderFiltro extends RecyclerView.ViewHolder{
        private TextView nombreGenero;
        private ImageView imagenGenero;

        public ViewHolderFiltro(View itemView) {
            super(itemView);
            nombreGenero = itemView.findViewById(R.id.nombreDelGeneroPocho_TextView);
            imagenGenero = itemView.findViewById(R.id.imagenDelGeneroPocho_ImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Filtro filtroSeleccionado = listaDeGenerosPocho.get(getAdapterPosition());
                    //new FragmentPeliculasFiltradas().cargarPeliculasFiltradas("16"/*filtroSeleccionado.getIdGenero()*/);    // TODO: Revisar si esto funciona
                    escuchadorDeFiltro.filtroAplicado(filtroSeleccionado);
                }
            });
        }

        public void cargaDeFiltro(Filtro filtro){
            nombreGenero.setText(filtro.getNombreGeneroPocho());
            imagenGenero.setImageResource(filtro.getImagenGeneroPocho());
        }
    }

    public interface EscuchadorDeFiltro {
        public void filtroAplicado(Filtro unFiltro);
    }
}

