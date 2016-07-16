package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class BibliotecaApp {
    private List<Book> bookList;
    private List<String> optionList;
    private MockedIO mockedIO;

    BibliotecaApp(List<Book> bookList, List<String> optionList, MockedIO mockedIO) {
        this.bookList = bookList;
        this.optionList = optionList;
        this.mockedIO = mockedIO;
    }

    void showWelcome() {
        mockedIO.output("Welcome, App started");
    }

    void listBooks() {
        for (Book book : bookList) {
            mockedIO.output(book.getName());
        }
    }

    void listBooksWithAllAttributes() {
        for (Book book : bookList) {
            mockedIO.output(book.getAll());
        }
    }

    void showOptions() {
        for (String option: optionList) {
            mockedIO.output(option);
        }
    }

    boolean selectOption() {
        switch (mockedIO.input()) {
            case "LB":
                listBooksWithAllAttributes();
                return true;
            case "QUIT":
                mockedIO.output("Over!");
                return false;
            default:
                mockedIO.output("Select a valid option!");
                return true;
        }
    }

    void keepCycle() {
        showOptions();
        while (true) {
            if (!selectOption()) {
                break;
            }
        }
    }

    public static void main(String args[]) {
        List<Book> bookList = new ArrayList<>();
        List<String> optionList = new ArrayList<>();
        bookList.add(new Book("myBook", "Sli", "2016"));
        optionList.add("[LB] List Book");
        optionList.add("[QUIT] Quit Program");

        BibliotecaApp bibliotecaApp = new BibliotecaApp(bookList, optionList, new MockedIO());
        bibliotecaApp.showWelcome();
        bibliotecaApp.keepCycle();
    }
}
