package com.sarathdr.movies.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author sdevadas on 11/06/16.
 */
public class Movie {

    @SerializedName("imdbID")
    public String id;

    @SerializedName("Title")
    public String title;

    @SerializedName("Year")
    public String year;


    @SerializedName("Genre")
    public String genre;

    @SerializedName("Director")
    public String director;

    @SerializedName("Actors")
    public String actors;

    @SerializedName("Poster")
    public String poster;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
