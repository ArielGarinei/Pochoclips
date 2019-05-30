package com.example.user.pochoclips.models.POJO;

import java.util.List;

/**
 * Created by Arielo on 19/7/2018.
 */

public class ContenedorMovie {
    private List<Movie> movieList;

    public ContenedorMovie(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

}

