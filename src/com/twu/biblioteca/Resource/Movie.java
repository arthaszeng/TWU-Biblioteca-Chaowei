package com.twu.biblioteca.resource;

import java.util.Stack;

public class Movie implements Resource {
    private String name;
    private String year;
    private String director;
    private String rating;
    private Stack<String> holdersStack = new Stack<>();

    public Movie(String name, String year, String director, String rating) {
        this.name = name;
        this.year = year;
        this.director = director;
        this.rating = rating;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAll() {
        return name + "\t" + year + "\t" + director + "\t" + rating;
    }

    @Override
    public void updateHolder(String libraryNumber) {
        holdersStack.push(libraryNumber);
    }
}
