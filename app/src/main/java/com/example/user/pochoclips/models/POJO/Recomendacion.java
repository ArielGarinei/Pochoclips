package com.example.user.pochoclips.models.POJO;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DH on 18/7/2018.
 */
@Entity
public class Recomendacion {
    public Recomendacion() {
    }

    public Recomendacion(@NonNull String idRecomendacion, String nombreRecomendador, String idRecomendador, String urlFotoRecomendador, Integer id, String backdropPath, String originalTitle, String overview, Double popularity, String posterPath, String releaseDate, String title, Double voteAverage) {
        this.idRecomendacion = idRecomendacion;
        this.nombreRecomendador = nombreRecomendador;
        this.idRecomendador = idRecomendador;
        this.urlFotoRecomendador = urlFotoRecomendador;
        this.id = id;
        this.backdropPath = backdropPath;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.title = title;
        this.voteAverage = voteAverage;
    }

    @PrimaryKey
    @NonNull
    private String idRecomendacion;
    private String nombreRecomendador;
    private String idRecomendador;
    private String urlFotoRecomendador;
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @SerializedName("original_title")
    @Expose
    private String originalTitle;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("popularity")
    @Expose
    private Double popularity;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;

    @NonNull
    public String getIdRecomendacion() {
        return idRecomendacion;
    }

    public void setIdRecomendacion(@NonNull String idRecomendacion) {
        this.idRecomendacion = idRecomendacion;
    }

    public String getNombreRecomendador() {
        return nombreRecomendador;
    }

    public void setNombreRecomendador(String nombreRecomendador) {
        this.nombreRecomendador = nombreRecomendador;
    }

    public String getIdRecomendador() {
        return idRecomendador;
    }

    public void setIdRecomendador(String idRecomendador) {
        this.idRecomendador = idRecomendador;
    }

    public String getUrlFotoRecomendador() {
        return urlFotoRecomendador;
    }

    public void setUrlFotoRecomendador(String urlFotoRecomendador) {
        this.urlFotoRecomendador = urlFotoRecomendador;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }
}
