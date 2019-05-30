package com.example.user.pochoclips.models.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Arielo on 20/6/2018.
 */

public class ContenedorCast {

    @SerializedName("cast")
    private List<Cast> castList;

    public ContenedorCast(List<Cast> castList) {
        this.castList = castList;
    }

    public List<Cast> getCastList() {
        return castList;
    }
}
