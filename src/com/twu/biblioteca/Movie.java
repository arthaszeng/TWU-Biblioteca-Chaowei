package com.twu.biblioteca;

//List Movies -
//        As a customer, I would like to see a list of available movies, so that I can browse for a movie that I might check-out.
//        Movies have a name, year, director and movie rating (from 1-10 or unrated).

public class Movie {
    private String name;
    private String year;
    private String director;
    private String rating;

    public Movie(String name, String year, String director, String rating) {
        this.name = name;
        this.year = year;
        this.director = director;
        this.rating = rating;
    }

    String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    String getRating() {
        return rating;
    }

    String getAll() {
        return name + "\t" + year + "\t" + director + "\t" + rating;
    }
}
