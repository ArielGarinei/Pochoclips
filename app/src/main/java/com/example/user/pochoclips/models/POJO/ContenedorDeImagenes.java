package com.example.user.pochoclips.models.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 24/7/2018.
 */

public class ContenedorDeImagenes {

    @SerializedName("backdrops")
    @Expose
    private List<Imagen> listaDeImagenes;

    public ContenedorDeImagenes(List<Imagen> listaDeImagenes) {
        this.listaDeImagenes = listaDeImagenes;
    }

    public List<Imagen> getListaDeImagenes() {
        return listaDeImagenes;
    }

    public void setListaDeImagenes(List<Imagen> listaDeImagenes) {
        this.listaDeImagenes = listaDeImagenes;
    }
}
