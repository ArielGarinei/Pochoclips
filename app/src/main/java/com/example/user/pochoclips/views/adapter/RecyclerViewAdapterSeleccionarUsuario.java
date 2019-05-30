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
import com.example.user.pochoclips.R;
import com.example.user.pochoclips.models.POJO.User;

import java.util.List;

/**
 * Created by DH on 21/7/2018.
 */

public class RecyclerViewAdapterSeleccionarUsuario extends RecyclerView.Adapter {
    private List<User> userList;
    private Context context;
    private EscuchadorDelRecyclerViewAdapterSeleccionarUsuario escuchadorDelRecyclerViewAdapterSeleccionarUsuario;

    public RecyclerViewAdapterSeleccionarUsuario(List<User> userList, EscuchadorDelRecyclerViewAdapterSeleccionarUsuario escuchadorDelRecyclerViewAdapterSeleccionarUsuario) {
        this.userList = userList;
        this.escuchadorDelRecyclerViewAdapterSeleccionarUsuario = escuchadorDelRecyclerViewAdapterSeleccionarUsuario;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View celda = layoutInflater.inflate(R.layout.celda_usuario_agregar,parent,false);
        return new UserViewHolder(celda);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        User userEnPosicion = userList.get(position);
        UserViewHolder userViewHolder = (UserViewHolder) holder;
        userViewHolder.bindUser(userEnPosicion);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
    private class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewFotoUsuarioAgregar;
        TextView textViewNombreDeUsuarioAgregar;
       // TextView textViewPuntuacionUsuarioAgregar;
        TextView textViewEmailUsuarioAgregar;
        public UserViewHolder(View itemView) {
            super(itemView);
            imageViewFotoUsuarioAgregar = itemView.findViewById(R.id.imageViewFotoUsuarioAgregar);
            textViewNombreDeUsuarioAgregar = itemView.findViewById(R.id.textViewNombreDeUsuarioAgregar);
            //textViewPuntuacionUsuarioAgregar = itemView.findViewById(R.id.textViewPuntuacionUsuarioAgregar);
            textViewEmailUsuarioAgregar = itemView.findViewById(R.id.textViewEmailUsuarioAgregar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer posicion = getAdapterPosition();
                    User user = userList.get(posicion);
                    escuchadorDelRecyclerViewAdapterSeleccionarUsuario.seleccionaronAlUsuario(user.getuId());
                }
            });
        }
        public void bindUser(User user){
            Glide.with(context).load(user.getPhotoPorfile()).into(imageViewFotoUsuarioAgregar);
            textViewNombreDeUsuarioAgregar.setText(user.getName());
//            textViewPuntuacionUsuarioAgregar.setText(user.getPuntajeRecomendador().toString());
            textViewEmailUsuarioAgregar.setText(user.getEmail());
        }
    }
    public interface EscuchadorDelRecyclerViewAdapterSeleccionarUsuario{
        public void seleccionaronAlUsuario(String userId);
    }
}
