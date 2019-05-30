package com.example.user.pochoclips.models.POJO;

/**
 * Created by Pat on 22/7/18.
 */

public class Favorito {
    Integer movieId;

    public Favorito(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
}
