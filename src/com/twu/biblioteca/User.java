package com.twu.biblioteca;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.List;

public class User {
    private String name;
    private String account;
    private String password;
    private List<Book> bookList;
    private List<Movie> movieList;

    public User(String name, String account, String password, List<Book> bookList, List<Movie> movieList) {
        this.name = name;
        this.account = account;
        this.password = password;
        this.bookList = bookList;
        this.movieList = movieList;
    }

    public String getName() {
        return name;
    }

    public String getAccount() {
        return account;
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
