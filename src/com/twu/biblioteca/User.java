package com.twu.biblioteca;

import java.util.List;

public class User {
    private String name;
    private String email;
    private String phone;
    private String libraryNumber;
    private String password;
    private List<Book> bookList;
    private List<Movie> movieList;

    public User(String name, String email, String phone, String libraryNumber, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.libraryNumber = libraryNumber;
        this.password = password;
    }

    String getName() {
        return name;
    }

    String getLibraryNumber() {
        return libraryNumber;
    }

    String getPassword() {
        return password;
    }

    String getEmail() {
        return email;
    }

    String getPhone() {
        return phone;
    }

    String showProfile() {
        return name + "\t" + email + "\t" + phone;
    }
}
