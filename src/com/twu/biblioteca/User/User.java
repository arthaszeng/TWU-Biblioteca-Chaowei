package com.twu.biblioteca.User;

import com.twu.biblioteca.Resource.Book;
import com.twu.biblioteca.Resource.Movie;

import java.util.List;

public class User {
    private String name;
    private String email;
    private String phone;
    private String libraryNumber;
    private String password;

    public User(String name, String email, String phone, String libraryNumber, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.libraryNumber = libraryNumber;
        this.password = password;
    }

    public String getLibraryNumber() {
        return libraryNumber;
    }

    String getPassword() {
        return password;
    }

    public String showProfile() {
        return name + "\t" + email + "\t" + phone;
    }
}
