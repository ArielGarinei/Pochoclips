package com.example.user.pochoclips.models.POJO;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity
public class Movie implements Serializable {

    public Movie() {
    }

    public Movie(Integer id, String backdropPath, String originalTitle, String overview, Double popularity, String posterPath, String releaseDate, String title, Double voteAverage, Integer favorito) {
        this.id = id;
        this.backdropPath = backdropPath;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.title = title;
        this.voteAverage = voteAverage;
        this.favorito = favorito;
        this.categoria = categoria;
    }

    @PrimaryKey
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

    private Integer favorito = 0;

    private String categoria;


   /* @SerializedName("adult")
    @Expose
    private Boolean adult;*/

    /*@SerializedName("budget")
    @Expose
    private Integer budget;*/

   /* @SerializedName("imdb_id")
    @Expose
    private String imdbId;*/

   /* @SerializedName("original_language")
    @Expose
    private String originalLanguage;*/

    /*@SerializedName("revenue")
    @Expose
    private Integer revenue;*/

   /* @SerializedName("runtime")
    @Expose
    private Integer runtime;*/

   /* @SerializedName("status")
    @Expose
    private String status;*/

   /* @SerializedName("tagline")
    @Expose
    private String tagline;*/

    /*@SerializedName("video")
    @Expose
    private Boolean video;*/

    /*@SerializedName("vote_count")
    @Expose
    private Integer voteCount;*/

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

    public Integer getFavorito() {
        return favorito;
    }

    public void setFavorito(Integer favorito) {
        this.favorito = favorito;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}