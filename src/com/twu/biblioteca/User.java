package com.twu.biblioteca;

import java.util.List;

public class User {
    private String name;
    private String libraryNumber;
    private String password;
    private List<Book> bookList;
    private List<Movie> movieList;

    public User(String name, String libraryNumber, String password, List<Book> bookList, List<Movie> movieList) {
        this.name = name;
        this.libraryNumber = libraryNumber;
        this.password = password;
        this.bookList = bookList;
        this.movieList = movieList;
    }

    public String getName() {
        return name;
    }

    public String getLibraryNumber() {
        return libraryNumber;
    }

    public String getPassword() {
        return password;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

}
