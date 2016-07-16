package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class BibliotecaApp {
    private List<Book> bookList;
    private List<String> optionList;

    BibliotecaApp(List<Book> bookList, List<String> optionList) {
        this.bookList = bookList;
        this.optionList = optionList;
    }

    void showWelcome() {
        System.out.println("Welcome, App started");
    }

    void showBookList() {
        for (Book book : bookList) {
            System.out.println(book.getName());
        }
    }

    void showBookListWithAttributes() {
        for (Book book : bookList) {
            System.out.println(book.getAll());
        }
    }

    void showOptions() {
        optionList.forEach(System.out::println);
    }
}
