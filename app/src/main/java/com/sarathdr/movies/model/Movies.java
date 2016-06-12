package com.sarathdr.movies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sdevadas on 11/06/16.
 */
public class Movies {

    private List<Movie> movies;

    public Movies() {
        this.movies = new ArrayList<Movie>();
    }

    public Movies(List<Movie> movies) {
        this.movies = movies;
    }


    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
