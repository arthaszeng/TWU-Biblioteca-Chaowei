package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class BibliotecaApp {
    List<Book> bookList;
    List<Book> checkBookList;
    private List<String> optionList;
    private MockedIO mockedIO;


    BibliotecaApp(List<Book> bookList, List<String> optionList, MockedIO mockedIO) {
        this.bookList = bookList;
        this.optionList = optionList;
        this.mockedIO = mockedIO;
    }

    BibliotecaApp(List<Book> bookList, List<Book> checkedBookList, List<String> optionList, MockedIO mockedIO) {
        this.bookList = bookList;
        this.checkBookList = checkedBookList;
        this.optionList = optionList;
        this.mockedIO = mockedIO;
    }

    void showWelcome() {
        mockedIO.output("Welcome, App started");
    }

    void listBooks(List<Book> bookList) {
        for (Book book : bookList) {
            mockedIO.output(book.getName());
        }
    }

    void listBooksWithAllAttributes(List<Book> bookList) {
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
        switch (mockedIO.input().toUpperCase()) {
            case "LB":
                listBooksWithAllAttributes(bookList);
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

    boolean checkoutOneBook() {
        String inputOfBookName = mockedIO.input();
        if (bookList.isEmpty()) {
            return false;
        } else {
            for (Book book : bookList) {
                if (book.getName().equals(inputOfBookName)) {
                    bookList.remove(book);
                    checkBookList.add(book);
                    mockedIO.output("Thank you! Enjoy the book");
                    return true;
                }
            }
            mockedIO.output("That book is not available");
            return false;
        }
    }
}
