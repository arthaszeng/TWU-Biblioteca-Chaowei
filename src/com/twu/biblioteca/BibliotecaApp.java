package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class BibliotecaApp {
    private List<Book> bookList = new ArrayList<>();

    BibliotecaApp(List<Book> bookList) {
        this.bookList = bookList;
    }

    void showWelcome() {
        System.out.println("Welcome, App started");
    }


    void showBookList() {
        for (Book aBookList : bookList) {
            System.out.println((aBookList).getName());
        }
    }
}
